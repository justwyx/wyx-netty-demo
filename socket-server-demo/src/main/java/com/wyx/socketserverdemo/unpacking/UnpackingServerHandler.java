package com.wyx.socketserverdemo.unpacking;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 * @Description : TODO 2020/10/31
 * @author : Just wyx
 * @Date : 2020/10/31
 */
public class UnpackingServerHandler extends SimpleChannelInboundHandler<String> {
	private int count = 0;


	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		// 将来自客户端的数据显示在服务端控制台
		System.out.println("server端接收到的第[" + count++ +"]个");
		System.out.println(ctx.channel().remoteAddress() + ", " + msg);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}
}
