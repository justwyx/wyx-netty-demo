package com.wyx.socketclientdemo.unpacking;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @Description :
 * 客户端作为发送方，向服务端发送两个大的 ByteBuf 数据包，
 * 这两个数据包会被拆分为 若干个 Frame 进行发送。这个过程中会发生拆包与粘包。
 * @author : Just wyx
 * @Date : 2020/10/31
 */
public class UnPackingClient {
	public static void main(String[] args) {
		NioEventLoopGroup group = new NioEventLoopGroup();

		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(group)
				.channel(NioSocketChannel.class)
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ChannelPipeline pipeline = ch.pipeline();
						// 客户端只需要发送数据 只需要编码就行了
						pipeline.addLast(new StringEncoder());
						pipeline.addLast(new UnPackingClientHandler());
					}
				});
		try {
			ChannelFuture future = bootstrap.connect("localhost", 8888).sync();
			System.out.println("连接服务端成功8888");
			future.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			group.shutdownGracefully();
		}
	}
}
