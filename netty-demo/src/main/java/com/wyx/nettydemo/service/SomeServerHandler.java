package com.wyx.nettydemo.service;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * @Description : 自定义服务端处理器
 * 需求：用户提交一个请求后，在浏览器上就会看到hello netty world
 * @author : Just wyx
 * @Date : 2020/10/30
 */
public class SomeServerHandler extends ChannelInboundHandlerAdapter {

	/**
	 * (what) : 当channel中有来自客户端的数据时就会触发该方法的执行
	 * (why) :
	 * (how) :
	 * @param ctx 上下文对象
	 * @param msg 来自客户端的数据
	 * @Author : Just wyx
	 * @Date : 18:42 2020/10/30
	 * @return : void
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//		System.out.println("msg = " + msg.getClass());
//		System.out.println("客户端地址 = " + ctx.channel().remoteAddress());

		if (msg instanceof HttpRequest) {
			HttpRequest request = (HttpRequest) msg;
			System.out.println("请求方式：" + request.method().name());
			System.out.println("请求uri：" + request.uri());

			// 构造response的响应体
			ByteBuf body = Unpooled.copiedBuffer("hello netty", CharsetUtil.UTF_8);
			// 生成响应对象
			DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, body);
			// 获取到response的头部后进行初始化
			HttpHeaders headers = response.headers();
			headers.set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
			headers.set(HttpHeaderNames.CONTENT_LENGTH, body.readableBytes());

			// 将响应对象写入到channel
//			ctx.write(response);
//			ctx.flush();
			ctx.writeAndFlush(response)
					// 添加监听器，响应体发送完毕
					.addListener(ChannelFutureListener.CLOSE);
		}

	}

	/**
	 * (what) : 当channel中的数据在处理过程中发生异常时会执行
	 * (why) :
	 * (how) :
	 * @param ctx 入参
     * @param cause 入参
	 * @Author : Just wyx
	 * @Date : 18:44 2020/10/30
	 * @return : void
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		// 关闭channel
		ctx.close();
	}
}
