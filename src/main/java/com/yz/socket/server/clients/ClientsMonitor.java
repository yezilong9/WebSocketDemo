package com.yz.socket.server.clients;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.TimerTask;

public class ClientsMonitor {
	private static final Logger logger = LoggerFactory.getLogger(ClientsMonitor.class);

	public ClientsMonitor() {
		Timer timer = new Timer("Socket monitor");
		/* schedule(TimerTask task, long delay, long period)
		       安排指定的任务从指定的延迟后开始进行重复的固定延迟执行。*/
		timer.schedule(new TimerTask() {
			public void run() {
				logger.info("Current online sockets number:" + ClientsManager.size());
//				logger.info("Current messages waiting for confirmed number:" + ConfirmedTimeoutManager.size());
			}
		}, 0, 30000);
	}

}
