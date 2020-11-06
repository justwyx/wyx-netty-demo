package com.wyx.socketserverdemo.FServer监听多个端口;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.UUID;

/**
 * @Description : TODO 2020/10/31
 * @author : Just wyx
 * @Date : 2020/10/31
 */
public class MultiPortsServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// 将来自客户端的数据显示在服务端控制台
		System.out.println("address:" + ctx.channel().remoteAddress() + ",port: " + ctx.channel().localAddress() + ",msg: " + msg);
		// 向客户端发送数据
		ctx.channel().writeAndFlush("from server:" + ",port: " + ctx.channel().localAddress() + ",uuid:" + UUID.randomUUID());
		Thread.sleep(1000);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}
}
