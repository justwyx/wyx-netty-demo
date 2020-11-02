package com.wyx.websocketdemo.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author : Just wyx
 * @Description : TODO 2020/10/31
 * @Date : 2020/10/31
 */
public class SomeServer {
	public static void main(String[] args) {
		// 用于处理客户端连接请求，将请求发送给childGroup中的eventLoop
		EventLoopGroup parentGroup = new NioEventLoopGroup();
		// 用于处理客户端请求
		EventLoopGroup childGroup = new NioEventLoopGroup();

		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(parentGroup, childGroup) // 指定 eventLoopGroup
					.channel(NioServerSocketChannel.class) // 指定使用nio进行通信
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							ChannelPipeline pipeline = ch.pipeline();
							// 添加http编解码器
							pipeline.addLast(new HttpServerCodec());
							// ChunkedWriteHandler 大块数据处理器
							pipeline.addLast(new ChunkedWriteHandler());
							// 添加chunk聚合处理器
							pipeline.addLast(new HttpObjectAggregator(4096));
							// 添加websocket协议转换处理器
							pipeline.addLast(new WebSocketServerProtocolHandler("/some"));
							pipeline.addLast(new SomeServerHandler());
						}
					}) // 指定 childGroup 中的 eventLoop 所绑定的线程所需要的处理器
			;

			// 绑定端口号，指定当前服务器所监听的端口号
			// bind()方法的执行是异步的
			ChannelFuture future = bootstrap.bind(8888).sync();
			System.out.println("启动成功8888 ");
			// 关闭 channel
			future.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			// 优雅关闭
			parentGroup.shutdownGracefully();
			childGroup.shutdownGracefully();
		}

	}
}
