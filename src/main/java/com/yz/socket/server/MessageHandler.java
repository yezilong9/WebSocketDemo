package com.yz.socket.server;


import io.netty.channel.Channel;
import com.yz.socket.message.ChatMessage;

public interface MessageHandler {

	void handle(Channel channel, ChatMessage message);

}
