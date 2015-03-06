package com.yz.socket.server;


import com.yz.socket.server.clients.ClientsMonitor;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebSocketServer extends Thread{

	public final static int DEFAULT_PORT = 5469;
    private final int port;
	private static final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);
    public WebSocketServer() {
    	this(DEFAULT_PORT);
    	this.start();
    }

    public WebSocketServer(int port) {
        this.port = port;
    }

	public void run(){
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(final SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new HttpResponseEncoder(), new HttpRequestDecoder(), new HttpObjectAggregator(65536),
							new WebSocketServerProtocolHandler("/websocket"),
							new CustomTextFrameHandler());
				}
			}).option(ChannelOption.SO_BACKLOG, 65536).childOption(ChannelOption.SO_KEEPALIVE, true).childOption(ChannelOption.TCP_NODELAY, true);
			logger.info("socket端口:"+port+"启动....");
			b.bind(port).sync().channel().closeFuture().sync();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	public static void main(String args[]){
		new WebSocketServer();
		new ClientsMonitor();
	}

}