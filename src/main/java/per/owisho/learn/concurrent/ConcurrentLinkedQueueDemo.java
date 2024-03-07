package per.owisho.learn.concurrent;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedQueueDemo {

    public static void main(String[] args) {

    }

    /**
     * ConcurrentLinkedQueue can poll null value from queue
     */
    public static void pollNull(){
        Queue<String> queue = new ConcurrentLinkedQueue<>();
        System.out.println(queue.poll());
    }

}
