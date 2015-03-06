package com.yz.socket.message;


import com.google.gson.Gson;

import java.nio.charset.Charset;

public class ChatMessage {
	public final static Charset charset = Charset.forName("ISO-8859-1");
	public final static int MESSAGE_MAX_LENGTH = 100000;
	private final static Gson gson = new Gson();

	// id
	private long id;
	//类型
	private int type;
	// 发送者userId
	private int sender;
	// 接受者id ?
	private int receiver;
	// 内容
	private String content;
	// 发送时间
	private String sendTime;
	
	public ChatMessage() {
	}
	
	public ChatMessage(ChatMessage message) {
		this.id = message.id;
		this.type = message.type;
		this.sender = message.sender;
		this.receiver = message.receiver;
		this.content = message.content;
		this.sendTime = message.sendTime;
	}

	public ChatMessage(int type, int sender, int receiver, String content, String sendTime) {
		this.type = type;
		this.sender = sender;
		this.receiver = receiver;
		this.content = content;
		this.sendTime = sendTime;
	}
	
	/**json数据转换为实体*/
	public static ChatMessage fromJson(String json) {
		return gson.fromJson(json, ChatMessage.class);
	}

	/**实体转成json*/
	public String toJson() {
		ChatMessage message = new ChatMessage(this);
		return gson.toJson(message, ChatMessage.class);
	}

	public int getType() {
		return type;
	}

	public int getSender() {
		return sender;
	}

	public int getReceiver() {
		return receiver;
	}

	public String getContent() {
		return content;
	}

	public String getSendTime() {
		return sendTime;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setSender(int sender) {
		this.sender = sender;
	}

	public void setReceiver(int receiver) {
		this.receiver = receiver;
	}
}
