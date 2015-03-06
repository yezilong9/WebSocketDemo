package com.yz.socket.message;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LineBasedFrameDecoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageDecoder extends LineBasedFrameDecoder {
	private final static Logger logger = LoggerFactory.getLogger(MessageDecoder.class);

	public MessageDecoder(final int maxLength) {
		super(maxLength);
	}

	@Override
	protected Object decode(ChannelHandlerContext ctx, ByteBuf buffer)
			throws Exception {
		ByteBuf byteBuf = (ByteBuf)super.decode(ctx, buffer);
		if (byteBuf == null) {
			return null;
		}

		String json = byteBuf.toString(ChatMessage.charset);
		logger.debug("Received json message: {}", json);
		try {
			return ChatMessage.fromJson(json);
		} catch (Exception ex) {
			logger.warn("Parsing json message error. json is " + json, ex);
			return null;
		}
	}

}
