package com.wyx.socketclientdemo.C1网络聊天;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * @Description :
 * 基于行的帧解码器，即会按照行分隔符对数据进行拆包粘包，解码出 ByteBuf。
 * @author : Just wyx
 * @Date : 2020/10/31
 */
public class WebChatClient {
	public static void main(String[] args) {
		NioEventLoopGroup group = new NioEventLoopGroup();

		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(group)
				.channel(NioSocketChannel.class)
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ChannelPipeline pipeline = ch.pipeline();
						pipeline.addLast(new LineBasedFrameDecoder(2048));
						pipeline.addLast(new StringDecoder());
						pipeline.addLast(new StringEncoder());
						pipeline.addLast(new WebChatClientHandler());
					}
				});
		try {
			ChannelFuture future = bootstrap.connect("localhost", 8888).sync();
			System.out.println("连接服务端成功8888");
			// 获取键盘输入
			InputStreamReader is = new InputStreamReader(System.in, "UTF-8");
			BufferedReader br = new BufferedReader(is);


			future.channel().writeAndFlush(br.readLine() + "\r\n");
		} catch (InterruptedException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
//			group.shutdownGracefully();
		}
	}
}
