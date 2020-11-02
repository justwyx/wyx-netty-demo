package com.wyx.socketserverdemo.B3固定长度帧解码器;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @Description :
 * 服务端作为接收方，直接将接收到的 Frame 解码为 String 后进行显示，不对这些 Frame 进行粘包与拆包。
 * @author : Just wyx
 * @Date : 2020/10/31
 */
public class FixedLengthServer {
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
						// 指定分隔符拆包粘包 DelimiterBasedFrameDecoder
						pipeline.addLast(new FixedLengthFrameDecoder(20));
						// 字符串解码器，只接收消息，不需要编码器
						pipeline.addLast(new StringDecoder());
						pipeline.addLast(new FixedLengthServerHandler());
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
