package com.sysadmin.zkServer;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;

public class ZkServer {

	// 连接的zookeeper地址和端口号
	private String connectString = "hadoop101:2181,hadoop102:2181,hadoop103:2181";

	// 会话超时时间，单位是毫秒
	private int sessionTimeout = 2000;

	private ZooKeeper zk = null;

	private String path = "/servers";

	// 与zk建立连接
	public void getconn() throws Exception {
		zk = new ZooKeeper(connectString, sessionTimeout, new Watcher() {

			@Override
			public void process(WatchedEvent event) {

				/*System.out.println(event.getType() + "--" + event.getPath());

				// 再次启动监听
				try {
					zk.getChildren(path, true);
				} catch (Exception e) {
					e.printStackTrace();
				}*/

			}
		});
	}

	// 注册
	public void register(String hostname) throws Exception {
		String create = zk.create(path + "/server", hostname.getBytes(), Ids.OPEN_ACL_UNSAFE,
				CreateMode.EPHEMERAL_SEQUENTIAL);
		System.out.println(hostname + " is online " + create);
	}

	// 业务功能
	public void business(String hostname) throws Exception {
		System.out.println(hostname + "  is working");

		Thread.sleep(Long.MAX_VALUE);
	}

	public static void main(String[] args) throws Exception {

		// 建立连接
		ZkServer zkServer = new ZkServer();
		zkServer.getconn();

		// 注册
		zkServer.register(args[0]);

		// 业务
		zkServer.business(args[0]);
	}
}
