package com.yz.socket.server.handler;


import com.yz.socket.server.MessageHandler;

public class HandlerFactory {

	public static MessageHandler loginHandler() {
		return new LoginHandler();
	}

	public static MessageHandler unknownTypeHandler() {
		return new UnknownTypeHandler();
	}

	public static MessageHandler sendMessageHandler() {
		return new SendMessageHandler();
	}

	public static MessageHandler terminateConnectHandler() {
		return new TerminateConnectHandler();
	}
	
}
