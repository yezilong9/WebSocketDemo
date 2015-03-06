package com.yz.socket.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MessageEncoder extends MessageToByteEncoder<ChatMessage> {
	private final static Logger logger = LoggerFactory.getLogger(MessageEncoder.class);

	@Override
	protected void encode(ChannelHandlerContext ctx, ChatMessage msg, ByteBuf out)
			throws Exception {
		String json = msg.toJson();
		logger.debug("Encoded json message: {}", json);
		out.writeBytes(json.getBytes(ChatMessage.charset))
		   .writeByte('\r').writeByte('\n');  //delimiter
	}

}
