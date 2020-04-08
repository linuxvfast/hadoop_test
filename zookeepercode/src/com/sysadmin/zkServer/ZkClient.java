package com.sysadmin.zkServer;

import java.util.ArrayList;
import java.util.List;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class ZkClient {

	// 连接的zookeeper地址和端口号
	private String connectString = "hadoop101:2181,hadoop102:2181,hadoop103:2181";

	// 会话超时时间，单位是毫秒
	private int sessionTimeout = 2000;

	private ZooKeeper zk = null;

	private String path = "/servers";

	// 建立连接
	public void getconn() throws Exception {
		zk = new ZooKeeper(connectString, sessionTimeout, new Watcher() {

			@Override
			public void process(WatchedEvent event) {
				System.out.println(event.getType() + "--" + event.getPath());
				try {
					lister();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// 监听节点
	public void lister() throws Exception {

		// 获取服务器子节点信息，并监听父节点进行监听
		List<String> children = zk.getChildren(path, true);
		ArrayList<String> arrayList = new ArrayList<>();

		// 获取节点的值
		for (String child : children) {
			byte[] data = zk.getData(path + "/" + child, false, null);

			arrayList.add(new String(data));

		}

		System.out.println(arrayList);

	}

	// 业务逻辑
	private void business() {

		System.out.println("start working");

		try {
			Thread.sleep(Long.MAX_VALUE);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		// 建立连接
		ZkClient zkClient = new ZkClient();
		zkClient.getconn();

		// 监听节点变化
		zkClient.lister();

		// 业务逻辑
		zkClient.business();
	}

}
