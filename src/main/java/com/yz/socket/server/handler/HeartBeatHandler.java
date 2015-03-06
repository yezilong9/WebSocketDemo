package com.yz.socket.server.handler;



import io.netty.channel.Channel;
import com.yz.socket.message.ChatMessage;
import com.yz.socket.message.MessageType;
import com.yz.socket.server.MessageHandler;
import com.yz.socket.server.clients.HeartBeatManager;
import com.yz.socket.util.Util;


public class HeartBeatHandler implements MessageHandler {

	public void handle(Channel channel, ChatMessage message) {
		int id = message.getSender();
		HeartBeatManager.delayTimeout(id);
		Util.sendServerMessage(channel, message.getSender(), "ok", MessageType.SUCCESS);
	}

}
