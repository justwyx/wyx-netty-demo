package com.wyx.socketclientdemo.C4心跳并重连;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.ScheduledFuture;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @Description : TODO 2020/10/31
 * @author : Just wyx
 * @Date : 2020/10/31
 */
public class SomeClientHandler extends ChannelInboundHandlerAdapter {
	private ScheduledFuture scheduled;
	private GenericFutureListener listener;

	private Bootstrap bootstrap;

	public SomeClientHandler(Bootstrap bootstrap) {
		this.bootstrap = bootstrap;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// 发送心跳
		sendHeartbeat(ctx.channel());
	}

	private void sendHeartbeat(Channel channel) {
		// 生成一个 [1, 8) 的随机数作为心跳的发送时间间隔
		int interval = new Random().nextInt(7) + 1;
		System.out.println(interval + "秒后会向Server发送心跳");
		this.scheduled = channel.eventLoop().schedule(() -> {
			if (channel.isActive()) {
				System.out.println("向Server发送心跳");
				channel.writeAndFlush("~ping");
			} else {
				System.out.println("与Server间的连接已经关闭");
			}
		}, interval, TimeUnit.SECONDS);

		listener = (future) -> sendHeartbeat(channel);
		// 向定时器增加监听器,再次发送心跳
		this.scheduled.addListener(listener);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// 一旦连接关闭，则将监听器移除，这样就不会再发生心跳方法的递归调用
		this.scheduled.removeListener(listener);

		bootstrap.connect("localhost", 8888).sync();
		System.out.println("服务重新连接成功8888");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}
}
