package com.yz.socket.server.clients;

import io.netty.util.Timeout;

import java.util.Map;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfirmedTimeoutManager {
    private static final Logger logger = LoggerFactory.getLogger(ConfirmedTimeoutManager.class);
	private static Map<String, Timeout> map = Maps.newConcurrentMap();
	
	public static void addTimeout(String id, Timeout timeout) {
		map.put(id, timeout);
        logger.info("addTimeout:"+id);
	}
	
	public static Timeout removeTimeout(String id) {
        logger.info("removeTimeout:"+id);
		return map.remove(id);
	}
	
	public static int size() {
		return map.size();
	}
}
