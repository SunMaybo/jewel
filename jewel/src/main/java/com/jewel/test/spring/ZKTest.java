package com.jewel.test.spring;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class ZKTest {

	public static void main(String[] args) throws IOException {
		ZooKeeper zk = new ZooKeeper("127.0.0.1:2181", 5000, new Watcher() {

			public void process(WatchedEvent arg0) {
				System.out.println("连接陈工");
			}

		});
		try {

			Stat statApp = zk.exists("/app", true);
			if (null == statApp) {
				zk.create("/app", "创建一个工程".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			}
		Stat statHost = zk.exists("/app" + "/" + "127.0.0.8", new Watcher() {

				public void process(WatchedEvent arg0) {
					System.out.println("1aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
					

				}
			});
			if (null == statHost) {
				zk.create("/app" + "/" + "127.0.0.8", "创建一个服务结点".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			}
		List<String>path=	zk.getChildren("/app", new Watcher() {

				public void process(WatchedEvent arg0) {
					System.out.println(arg0.getPath());
					System.out.println(arg0.getType()==EventType.NodeCreated);
					System.out.println(arg0.getType()==EventType.NodeDataChanged);
					System.out.println(arg0.getType()==EventType.NodeDeleted);
					System.out.println(arg0.getType()==EventType.NodeChildrenChanged);
					System.out.println(arg0.getType()==EventType.None);
					System.out.println("/app" + " childern de watcher" + "-----------" + arg0.getPath());
				}
			});
		//System.out.println(path.toString());

/*			Stat stat = zk.exists("/app" + "/" + "127.0.0.4", new Watcher() {

				public void process(WatchedEvent arg0) {
					System.out.println("3422222222222222222222222222222222222222222222");
				}
			});
			if (null != stat) {
				zk.getData("/app" + "/" + "127.0.0.4", new Watcher() {

					public void process(WatchedEvent arg0) {
						System.out.println("dsgdsgdsgdsgdsggdsdgsw346436ryerye");
					}
				}, new Stat());
			}*/

			Thread.sleep(44647447);
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
