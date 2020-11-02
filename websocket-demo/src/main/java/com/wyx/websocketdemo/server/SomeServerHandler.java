package com.wyx.websocketdemo.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.UUID;

/**
 * @Description : TODO 2020/10/31
 * @author : Just wyx
 * @Date : 2020/10/31
 */
public class SomeServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String text = ((TextWebSocketFrame) msg).text();
		ctx.channel().writeAndFlush(new TextWebSocketFrame("Frome Client:" + text));
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
