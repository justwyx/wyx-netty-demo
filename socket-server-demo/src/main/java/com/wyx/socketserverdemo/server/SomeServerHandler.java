package com.wyx.socketserverdemo.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.UUID;

/**
 * @Description : TODO 2020/10/31
 * @author : Just wyx
 * @Date : 2020/10/31
 */
public class SomeServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// 将来自客户端的数据显示在服务端控制台
		System.out.println(ctx.channel().remoteAddress() + ", " + msg);
		// 向客户端发送数据
		ctx.channel().writeAndFlush("from server:" + UUID.randomUUID());
		Thread.sleep(500);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}
}
