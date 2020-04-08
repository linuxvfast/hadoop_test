package com.sysadmin.zkServer;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;

public class ZkServer {

	// ���ӵ�zookeeper��ַ�Ͷ˿ں�
	private String connectString = "hadoop101:2181,hadoop102:2181,hadoop103:2181";

	// �Ự��ʱʱ�䣬��λ�Ǻ���
	private int sessionTimeout = 2000;

	private ZooKeeper zk = null;

	private String path = "/servers";

	// ��zk��������
	public void getconn() throws Exception {
		zk = new ZooKeeper(connectString, sessionTimeout, new Watcher() {

			@Override
			public void process(WatchedEvent event) {

				/*System.out.println(event.getType() + "--" + event.getPath());

				// �ٴ���������
				try {
					zk.getChildren(path, true);
				} catch (Exception e) {
					e.printStackTrace();
				}*/

			}
		});
	}

	// ע��
	public void register(String hostname) throws Exception {
		String create = zk.create(path + "/server", hostname.getBytes(), Ids.OPEN_ACL_UNSAFE,
				CreateMode.EPHEMERAL_SEQUENTIAL);
		System.out.println(hostname + " is online " + create);
	}

	// ҵ����
	public void business(String hostname) throws Exception {
		System.out.println(hostname + "  is working");

		Thread.sleep(Long.MAX_VALUE);
	}

	public static void main(String[] args) throws Exception {

		// ��������
		ZkServer zkServer = new ZkServer();
		zkServer.getconn();

		// ע��
		zkServer.register(args[0]);

		// ҵ��
		zkServer.business(args[0]);
	}
}
