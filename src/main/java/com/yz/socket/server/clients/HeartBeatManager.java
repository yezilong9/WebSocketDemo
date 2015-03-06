package com.yz.socket.server.clients;


import io.netty.util.Timeout;
import io.netty.util.TimerTask;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.yz.socket.util.TimingWheel;

/**
 * 模拟心跳包
 */
public class HeartBeatManager {
	private static final Logger logger = LoggerFactory.getLogger(HeartBeatManager.class);
	private static final ConcurrentMap<Integer, Timeout> map = Maps.newConcurrentMap();
	private static final int HEARTBEAT_TIMEOUT = 5; //5分钟收不到心跳则认为超时
	
	public static void addTimeout(final Integer id) {
		Timeout timeout = TimingWheel.newTimeout(new TimerTask() {
			public void run(Timeout timeout) throws Exception {
				logger.debug("{} heartbeat timeout!", id);
				ClientsManager.remove(id);
				map.remove(id);
			}
		}, HEARTBEAT_TIMEOUT, TimeUnit.MINUTES);
		
		Timeout old = map.put(id, timeout);
		if (old != null) {
			//如果之前存在timeout，那么这个应该取消掉
			old.cancel();
		}
	}
	
	public static void delayTimeout(Integer id) {
		Timeout old = map.remove(id);

		//如果timeout已经被取消或过期，就不存在推迟的问题了，直接返回
		if (old == null || old.isCancelled() || old.isExpired()) {
			return;
		}

		old.cancel();
		Timeout delayed = TimingWheel.newTimeout(old.task(), HEARTBEAT_TIMEOUT, TimeUnit.MINUTES);
		Timeout result = map.put(id, delayed);
		if (result != null) {
			//如果在这个方法执行过程中又增加了timeout，则取消掉，以最新添加的为准
			result.cancel();
		}
	}
	
	public static void removeTimeout(Integer id) {
		Timeout timeout = map.remove(id);
		if (timeout != null) {
			timeout.cancel();
		}
	}
}
