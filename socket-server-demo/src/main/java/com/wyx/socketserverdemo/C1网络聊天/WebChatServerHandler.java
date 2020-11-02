package com.wyx.socketserverdemo.C1网络聊天;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @Description : TODO 2020/10/31
 * @author : Just wyx
 * @Date : 2020/10/31
 */
public class WebChatServerHandler extends ChannelInboundHandlerAdapter {
	// 创建一个channelGroup，其是一个线程安全的，其中存放着与当前服务器相连接的所有Active状态的 channel
	// GlobalEventExecutor是一个单例、单线程的eventExecutor，是为了保证当前group中的所有channel的处理线程是同一个线程
	private static ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


	/**
	 * 只要有客户端channel给当前的服务端发送了消息，那么就会触发该方法的执行
	 * @param ctx
	 * @param msg
	 * @throws Exception
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// 获取到向服务器发送消息的channel
		Channel channel = ctx.channel();
		// 这里要实现将消息广播给所有group中的客户端channel
		// 发送给自己的消息与发送给大家的消息不一样
		group.forEach(ch -> {
			if (ch != channel) {
				ch.writeAndFlush(channel.remoteAddress() + "." + msg + "\n");
			} else {
				channel.writeAndFlush("me:" + msg + "\n");
			}
		});
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		System.out.println(channel.remoteAddress() +"========上线啦");
		group.writeAndFlush(channel.remoteAddress() +"========上线啦\n");
		group.add(channel);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		System.out.println(channel.remoteAddress() +"-------------------下线啦");
		group.writeAndFlush(channel.remoteAddress() +"-------------------下线啦\n");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}
}
