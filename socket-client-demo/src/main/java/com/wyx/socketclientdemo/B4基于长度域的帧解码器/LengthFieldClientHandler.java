package com.wyx.socketclientdemo.B4基于长度域的帧解码器;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Description : 不需要接收消息，只发送消息
 * @author : Just wyx
 * @Date : 2020/10/31
 */
public class LengthFieldClientHandler extends ChannelInboundHandlerAdapter {

	private String message = "012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789" +
			"012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789";

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
//		ctx.channel().writeAndFlush(message);
//		ctx.channel().writeAndFlush(message);
		byte[] bytes = message.getBytes();
		ByteBuf byteBuf;

		for (int i = 0; i < 2; i++) {
			// 申请缓存空间
			byteBuf = Unpooled.buffer(bytes.length);
			// 将数据写入到缓存
			byteBuf.writeBytes(bytes);
			// 将缓存中的数据写入到channel
			ctx.writeAndFlush(byteBuf);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}
}
