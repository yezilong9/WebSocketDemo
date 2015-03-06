package com.yz.socket.server;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import com.yz.socket.message.ChatMessage;
import com.yz.socket.message.MessageType;
import com.yz.socket.server.clients.ClientsManager;
import com.yz.socket.server.handler.HandlerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomTextFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

	private static final Logger logger = LoggerFactory.getLogger(CustomTextFrameHandler.class);


	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		logger.info("初始化...");
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) {
		ClientsManager.remove(ctx.channel());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
        ClientsManager.remove(ctx.channel());
	}

	@Override
	protected void messageReceived(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
		String request = textWebSocketFrame.text();
		logger.info("接收到数据为："+request);
		ChatMessage msg = ChatMessage.fromJson(request);
		switch (msg.getType()) {
			case MessageType.LOGIN:
				HandlerFactory.loginHandler().handle(channelHandlerContext.channel(), msg);
				break;
			case MessageType.TERMINATE_CONNECT:
				HandlerFactory.terminateConnectHandler().handle(channelHandlerContext.channel(), msg);
				break;
			default:
				HandlerFactory.unknownTypeHandler().handle(channelHandlerContext.channel(), msg);
				break;
		}
	}
}