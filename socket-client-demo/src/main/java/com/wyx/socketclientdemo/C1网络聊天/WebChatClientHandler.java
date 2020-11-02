package com.wyx.socketclientdemo.C1网络聊天;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Description : 不需要接收消息，只发送消息
 * @author : Just wyx
 * @Date : 2020/10/31
 */
public class WebChatClientHandler extends SimpleChannelInboundHandler<String> {


	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		System.out.println(msg);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}


}
