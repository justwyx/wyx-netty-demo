package com.wyx.socketserverdemo.FServer监听多个端口;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Just wyx
 * @Description : TODO 2020/10/31
 * @Date : 2020/10/31
 */
public class MultiPortsServer {
	private List<ChannelFuture> channelFutures = new ArrayList<>();
	private NioEventLoopGroup parentGroup = new NioEventLoopGroup();
	private NioEventLoopGroup childGroup = new NioEventLoopGroup();

	public void start(List<Integer> ports) throws InterruptedException {
		ServerBootstrap bootstrap = new ServerBootstrap()
				.group(parentGroup, childGroup)
				.channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ChannelPipeline pipeline = ch.pipeline();
						// 字符串解码器
						pipeline.addLast(new StringDecoder());
						// 字符串编码器
						pipeline.addLast(new StringEncoder());
						pipeline.addLast(new MultiPortsServerHandler());
					}
				});

		for (Integer port : ports) {
			ChannelFuture future = bootstrap.bind(port).sync();
			System.out.println("服务器正在启动中。。。。。。。端口为：" + port);
			future.addListener(f -> {
				if (f.isSuccess()) {
					System.out.println("服务器已启动，监听端口为：" + port);
				}
			});
			// 将所有future添加到集合中
			channelFutures.add(future);
		}
	}

	public void closeAllChannel() {
		for (ChannelFuture future : channelFutures) {
			future.channel().close();
		}
		parentGroup.shutdownGracefully();
		childGroup.shutdownGracefully();
		System.out.println("所有channel已经全部关闭");
	}
}
