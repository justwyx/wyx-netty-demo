package com.wyx.nettydemo.service;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @Description : 管道初始化器
 * @author : Just wyx
 * @Date : 2020/10/30
 */
public class SomeChannelInitializer extends ChannelInitializer<SocketChannel> {
	// 当Channel初始创建完毕后就会触发该方法的执行，用于初始化channel
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		// 从channel中获取pipeline
		ChannelPipeline pipeline = ch.pipeline();
		// 将HttpServerCodec处理器放入到pipeline的最后
		pipeline.addLast(new HttpServerCodec());
		// 将自定义的处理器放入到pipeline的最后
		pipeline.addLast(new SomeServerHandler());
	}
}
