package com.wyx.socketserverdemo.B1行分隔符拆包粘包;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @Description :
 * 基于行的帧解码器，即会按照行分隔符对数据进行拆包粘包，解码出 ByteBuf。
 * @author : Just wyx
 * @Date : 2020/10/31
 */
public class LineServer {
	public static void main(String[] args) {
		NioEventLoopGroup parentGroup = new NioEventLoopGroup();
		NioEventLoopGroup childGroup = new NioEventLoopGroup();

		ServerBootstrap bootstrap = new ServerBootstrap()
				.group(parentGroup, childGroup)
				.channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ChannelPipeline pipeline = ch.pipeline();
						// 按换行接收
						pipeline.addLast(new LineBasedFrameDecoder(5120)); // 5k
						// 字符串解码器，只接收消息，不需要编码器
						pipeline.addLast(new StringDecoder());
						pipeline.addLast(new LineServerHandler());
					}
				});

		try {
			ChannelFuture future = bootstrap.bind(8888).sync();
			System.out.println("服务器已启动8888");
			future.channel().closeFuture().sync();
			System.out.println("服务器已关闭8888");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			parentGroup.shutdownGracefully();
			childGroup.shutdownGracefully();
		}

	}
}
