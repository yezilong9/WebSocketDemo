package com.yz.socket.util;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import com.yz.socket.message.ChatMessage;
import com.yz.socket.server.clients.ClientsManager;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
	public static String geTimeNoS() {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = df.format(date);
		return time;
	}

	public static String geTime() {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = df.format(date);
		return time;
	}

	/**
	 * 服务器发送的消息
	 * 
	 * @param dfAccount
	 * @param content
	 * @param type
	 */

	public static void sendServerMes(int dfAccount, String content, int type) {
		sendServerMessage(dfAccount, content, type);
	}

	public static void sendServerMessage(Channel channel, int userId, String content, int type) {
		ChatMessage message = new ChatMessage(type, 0, userId, content, geTimeNoS());
		channel.writeAndFlush(new TextWebSocketFrame(message.toJson().toString()));// websocket特有的输出形式
	}

	public static void sendServerMessage(int userId, String content, int type) {
		Channel channel = ClientsManager.getUserChannel(userId);
		if (channel != null) {
			sendServerMessage(channel, userId, content, type);
		}
	}


}
