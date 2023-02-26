package com.example.mxbeans;

import java.lang.management.ManagementFactory;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import javax.management.MBeanServer;
import javax.management.ObjectName;

public class Main{
	public static void main(String[] args) throws Exception{
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		ObjectName name = new ObjectName("com.example.mxbeans:type=QueueSampler");
		Queue<String> queue = new ArrayBlockingQueue<String>(10);
		queue.add("Request-1");
		queue.add("Request-2");
		queue.add("Request-3");
		QueueSampler mxbean = new QueueSampler(queue);

		mbs.registerMBean(mxbean,name);

		System.out.println("Waiting...");
		Thread.sleep(Long.MAX_VALUE);
	}
}
