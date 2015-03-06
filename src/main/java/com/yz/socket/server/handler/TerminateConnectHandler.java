package com.yz.socket.server.handler;


import io.netty.channel.Channel;
import com.yz.socket.message.ChatMessage;
import com.yz.socket.server.MessageHandler;


public class TerminateConnectHandler implements MessageHandler {

	public void handle(Channel channel, ChatMessage message) {
		channel.close();
	}

}
