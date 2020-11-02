package com.wyx.socketclientdemo.stickybag;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Description : 不需要接收消息，只发送消息
 * @author : Just wyx
 * @Date : 2020/10/31
 */
public class StickyBagClientHandler extends ChannelInboundHandlerAdapter {

	private String message = "hello world";

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		for (int i = 0; i < 1000; i++) {
			ctx.channel().writeAndFlush(message);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}
}
