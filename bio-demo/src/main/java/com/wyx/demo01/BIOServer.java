package com.wyx.demo01;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Description : BIO 服务器程序
 * @author : Just wyx
 * @Date : 2020/11/17
 */
public class BIOServer {
	public static void main(String[] args) throws IOException {
		// 创建ServerSocket对象
		ServerSocket serverSocket = new ServerSocket(9999);
		while (true) {
			System.out.println("开始阻塞式监听客户端连接...");
			// 阻塞方式 监听客户端连接
			Socket accept = serverSocket.accept();
			System.out.println("阻塞式-从连接中获取输入流信息...");
			// 阻塞方式 从连接中获取输入流信息
			InputStream inputStream = accept.getInputStream();
			byte[] bytes = new byte[1024];
			inputStream.read(bytes);
			String message = new String(bytes);
			// 获取地址
			String ipAddress = accept.getInetAddress().getHostAddress();
			System.out.println("客户端ip:" + ipAddress + " message: " + message);

			// 从连接中获取输出流并回话
			OutputStream outputStream = accept.getOutputStream();
			outputStream.write(("服务端说我收到了:" + message).getBytes());
			// 关闭
			accept.close();
		}
	}
}
