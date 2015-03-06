package com.yz.socket.server.handler;

import io.netty.channel.Channel;
import com.yz.socket.message.ChatMessage;
import com.yz.socket.server.MessageHandler;
import com.yz.socket.server.clients.ClientsManager;
import com.yz.socket.util.Util;


public class SendMessageHandler implements MessageHandler {

	public void handle(Channel channel, ChatMessage message) {
		try {
			Channel to = ClientsManager.getUserChannel(message.getReceiver());
			if (to != null) {
				message.setSendTime(Util.geTimeNoS());
				to.writeAndFlush(message);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
