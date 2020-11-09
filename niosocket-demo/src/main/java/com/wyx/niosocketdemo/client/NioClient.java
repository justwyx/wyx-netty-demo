package com.wyx.niosocketdemo.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author : Just wyx
 * @Description : TODO 2020/11/9
 * @Date : 2020/11/9
 */
public class NioClient {
	public static void main(String[] args) throws IOException {
		// 创建客户端channel
		SocketChannel clientChannel = SocketChannel.open();
		// 指定channel使用非阻塞模式
		clientChannel.configureBlocking(false);
		// 指定要连接的Server地址
		InetSocketAddress serverAddr = new InetSocketAddress("localhost", 8888);
		// 连接server
		if (!clientChannel.connect(serverAddr)) {
			// 首次连接失败，进行重连
			while (clientChannel.finishConnect()) {
				continue;
			}
		}
		clientChannel.write(ByteBuffer.wrap("hello".getBytes()));
		System.out.println("client消息已发送");

		System.in.read();
	}
}
