package com.wyx.socketserverdemo.C2读写空闲检测;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.UUID;

/**
 * @Description : TODO 2020/10/31
 * @author : Just wyx
 * @Date : 2020/10/31
 */
public class SomeServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent event = (IdleStateEvent) evt;
			String eventDes = null;
			switch (event.state()) {
				case READER_IDLE:
					eventDes = "读空闲超时";
					break;
				case WRITER_IDLE:
					eventDes = "写空闲超时";
					break;
				case ALL_IDLE:
					eventDes = "读和写空闲超时";
					break;
			}
			System.out.println(eventDes);
			// 关闭连接
			ctx.close();
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}
}
