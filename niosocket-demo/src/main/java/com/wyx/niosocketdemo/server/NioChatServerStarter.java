package com.wyx.niosocketdemo.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

/**
 * @author : Just wyx
 * @Description : TODO 2020/11/9
 * @Date : 2020/11/9
 */
public class NioChatServerStarter {
	public static void main(String[] args) throws IOException {
		// 创建一个服务端channel
		ServerSocketChannel serverChannel = ServerSocketChannel.open();
		// 指定channel采用的为阻塞模式
		serverChannel.configureBlocking(false);
		// 指定要监听端口
		serverChannel.bind(new InetSocketAddress(8888));
		// 创建一个多路复用selector
		Selector selector = Selector.open();

		NioChatServer chatServer = new NioChatServer();
		chatServer.enableChat(serverChannel, selector);

	}
}
