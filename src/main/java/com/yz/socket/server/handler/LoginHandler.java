package com.yz.socket.server.handler;


import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yz.socket.message.ChatMessage;
import com.yz.socket.message.MessageType;
import com.yz.socket.server.MessageHandler;
import com.yz.socket.server.clients.ClientsManager;
import com.yz.socket.server.clients.push.ContentPush;

public class LoginHandler implements MessageHandler {
	
	
	private static final Logger logger = LoggerFactory.getLogger(LoginHandler.class);

	public void handle(Channel channel, ChatMessage message) {
		ChatMessage msg = new ChatMessage();
		msg.setType(MessageType.SUCCESS);
		ClientsManager.add(channel, message.getSender());
		ContentPush.add(message.getSender(), channel);
		channel.writeAndFlush(new TextWebSocketFrame(msg.toJson()));
		logger.info("User login result:" + msg.toJson());
	}
}
