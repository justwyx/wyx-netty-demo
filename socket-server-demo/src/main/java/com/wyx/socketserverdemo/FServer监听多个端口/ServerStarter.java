package com.wyx.socketserverdemo.FServer监听多个端口;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Just wyx
 * @Description : TODO 2020/11/4
 * @Date : 2020/11/4
 */
public class ServerStarter {
	public static void main(String[] args) throws InterruptedException {
		List<Integer> ports = new ArrayList<>();
		ports.add(7777);
		ports.add(8888);
		ports.add(9999);
		MultiPortsServer server = new MultiPortsServer();
		// 启动服务器，按照指定端口号时迁监听
		server.start(ports);
		// 30秒后关闭
		Thread.sleep(30 * 1000);
		server.closeAllChannel();
	}
}
