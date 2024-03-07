package per.owisho.learn.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class LockConditionDemo {

    public static void main(String[] args) {
        LockConditionDemo demo = new LockConditionDemo();
        StackDemo stack = demo.new StackDemo();
        for (int i = 0; i < 10; i++) {
            new Thread(demo.new PopThread(stack)).start();
        }
        for (int i = 0; i < 10; i++) {
            new Thread(demo.new PushThread(stack)).start();
        }
    }

    private class PopThread implements Runnable {
        private StackDemo stack;

        public PopThread(StackDemo stack) {
            this.stack = stack;
        }

        @Override
        public void run() {
            String data = stack.pop();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "消费：" + data);
        }
    }

    private class PushThread implements Runnable {
        private StackDemo stack;

        public PushThread(StackDemo stack) {
            this.stack = stack;
        }

        @Override
        public void run() {
            stack.push(Thread.currentThread().getName() + "生产数据");
        }
    }

    /**
     * 模拟一个stack存储，如果无数据，取数据时进行等待
     */
    private class StackDemo {
        private Lock lock = new ReentrantLock();
        private Condition emptyCondition = lock.newCondition();

        List<String> datas = new ArrayList<>();

        public void push(String data) {
            lock.lock();
            try {
                datas.add(data);
                emptyCondition.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public String pop() {
            lock.lock();
            try {
                while (datas.isEmpty())
                    emptyCondition.await();

                int index = datas.size() - 1;
                String data = datas.get(index);
                datas.remove(index);
                return data;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
            return null;
        }


    }

}
