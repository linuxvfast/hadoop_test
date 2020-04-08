package com.sysadmin.zookeeper;

import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

public class ZkClient {

	// 连接的zookeeper地址和端口号
	private String connectString = "hadoop101:2181,hadoop102:2181,hadoop103:2181";

	// 会话超时时间，单位是毫秒
	private int sessionTimeout = 2000;

	private ZooKeeper zkclient = null;

	@Before
	public void initzk() throws Exception {
		zkclient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {

			@Override
			public void process(WatchedEvent event) {

				// 触发监听后执行的操作
				System.out.println(event.getType() + "--" + event.getPath());

				try {
					zkclient.getChildren("/", true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				/*
				 * try { zkclient.exists("/sysadmin", true); } catch (KeeperException e) { //
				 * TODO Auto-generated catch block e.printStackTrace(); } catch
				 * (InterruptedException e) { // TODO Auto-generated catch block
				 * e.printStackTrace(); }
				 */

			}
		});
	}

	// 创建子节点
//	@Test
	public void subcreate() throws Exception {

		String subnode = zkclient.create("/sysadmin", "hellozk".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		System.out.println(subnode);

	}

	// 获取所有子节点
	@Test
	public void getnode() throws Exception {
		List<String> children = zkclient.getChildren("/", true);

		for (String child : children) {

			System.out.println(child);
		}

		Thread.sleep(Long.MAX_VALUE);

	}

	// 获取节点的值
//	@Test
	public void getvalue() throws Exception {
		byte[] value = zkclient.getData("/sysadmin/file1", true, null);
		System.out.println(value);
	}

	// 判断节点是否存在
//	@Test
	public void checknodeexists() throws Exception {
		Stat exists = zkclient.exists("/sysadmin", true);

		System.out.println(exists == null ? "not exits" : "exits");

		Thread.sleep(Long.MAX_VALUE);
	}
}
