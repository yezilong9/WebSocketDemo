package com.yz.socket.server.handler;


import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yz.socket.message.ChatMessage;
import com.yz.socket.message.MessageType;
import com.yz.socket.server.MessageHandler;
import com.yz.socket.server.clients.ClientsManager;
import com.yz.socket.util.Util;


public class UnknownTypeHandler implements MessageHandler {
	private final static Logger logger = LoggerFactory.getLogger(UnknownTypeHandler.class);

	public void handle(Channel channel, ChatMessage message) {
		ClientsManager.add(channel, message.getSender());
		Util.sendServerMes(message.getSender(),"不知道你传什么过来呢！", MessageType.FAIL);
		logger.warn("Received unknown message type: " + message.getType());
	}

}
