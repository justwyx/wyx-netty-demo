package com.wyx.socketclientdemo.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;

/**
 * @Description : TODO 2020/10/31
 * @author : Just wyx
 * @Date : 2020/10/31
 */
public class SomeClientHandler extends SimpleChannelInboundHandler<String> {
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		System.out.println(ctx.channel().remoteAddress() + " ," + msg);
		ctx.channel().writeAndFlush("from client:" + LocalDateTime.now());
		Thread.sleep(500);
	}

	/**
	 * (what) : 当channel被激活后会触发该方法的执行
	 * (why) :
	 * (how) :
	 * @param ctx 入参
	 * @Author : Just wyx
	 * @Date : 15:04 2020/10/31
	 * @return : void
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.channel().writeAndFlush("我开始发消息啦");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}
}
