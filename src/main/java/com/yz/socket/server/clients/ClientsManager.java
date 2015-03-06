package com.yz.socket.server.clients;


import io.netty.channel.Channel;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;


public class ClientsManager {
	private static final Logger logger = LoggerFactory.getLogger(ClientsManager.class);
	
	private static final Map<Integer, Channel> userChannelMap = Maps.newConcurrentMap();
	private static final Map<Channel, Integer> channelUserMap = Maps.newConcurrentMap();
	
	
	public static void add(Channel channel, Integer userId) {
		logger.info("User " + userId + " login...");
		userChannelMap.put(userId, channel);
		channelUserMap.put(channel, userId);
	}

	public static Channel getUserChannel(Integer userId) {
		return userChannelMap.get(userId); 
	}

	public static boolean isOnline(Integer userId) {
		return userChannelMap.containsKey(userId);
	}

	public static void remove(Channel channel) {
		Integer userId = channelUserMap.remove(channel);
		if (userId != null) {
			logger.info("User " + userId + " logout...");
			userChannelMap.remove(userId);
			HeartBeatManager.removeTimeout(userId);
		}
	}
	
	public static void remove(Integer userId) {
		logger.info("User " + userId + " logout...");
		Channel channel = userChannelMap.remove(userId);
		if (channel != null) {
			channelUserMap.remove(channel);
		}
	}
	
	public static int size() {
		return userChannelMap.size();
	}
}
