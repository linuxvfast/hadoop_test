package com.sysadmin.zkServer;

import java.util.ArrayList;
import java.util.List;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class ZkClient {

	// ���ӵ�zookeeper��ַ�Ͷ˿ں�
	private String connectString = "hadoop101:2181,hadoop102:2181,hadoop103:2181";

	// �Ự��ʱʱ�䣬��λ�Ǻ���
	private int sessionTimeout = 2000;

	private ZooKeeper zk = null;

	private String path = "/servers";

	// ��������
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

	// �����ڵ�
	public void lister() throws Exception {

		// ��ȡ�������ӽڵ���Ϣ�����������ڵ���м���
		List<String> children = zk.getChildren(path, true);
		ArrayList<String> arrayList = new ArrayList<>();

		// ��ȡ�ڵ��ֵ
		for (String child : children) {
			byte[] data = zk.getData(path + "/" + child, false, null);

			arrayList.add(new String(data));

		}

		System.out.println(arrayList);

	}

	// ҵ���߼�
	private void business() {

		System.out.println("start working");

		try {
			Thread.sleep(Long.MAX_VALUE);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		// ��������
		ZkClient zkClient = new ZkClient();
		zkClient.getconn();

		// �����ڵ�仯
		zkClient.lister();

		// ҵ���߼�
		zkClient.business();
	}

}
