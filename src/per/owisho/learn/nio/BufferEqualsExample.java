package per.owisho.learn.nio;

import java.nio.ByteBuffer;

/**
 * buffer equals 方法测试类
 * buffer 的 equals 方法比较内容仅为
 * 剩余内存类型和剩余空间大小、
 * 以及剩余空间中的内容（因为buffer可以多次存取，clear等函数只修改position，limit等标记位置，并未清空buffer中实际内容）；
 * 与当前存储内容无关
 */
public class BufferEqualsExample {

    public static void main(String[] args) {
        //初始化两个大小不同的buffer，结果两者equals为false
        ByteBuffer bf1 = ByteBuffer.allocate(10);
        ByteBuffer bf2 = ByteBuffer.allocate(5);
        System.out.println("bf1 equals bf2=" + bf1.equals(bf2));

        //将大空间buffer写入数据，让剩余空间等于小空间buffer，结果两者equals为true
        byte[] bytes = new byte[]{1, 2, 3, 4, 5};
        System.out.println("bytes length=" + bytes.length);
        bf1.put(bytes);
        System.out.println("bf1 equals bf2=" + bf1.equals(bf2));

        //当大空间缓存剩余空间的内容与小空间缓存剩余空间内容不相同时，结果两者equals为false
        bf1.put((byte) 1);
        bf1.flip();
        bf1.get();
        System.out.printf("bf1 remaining size=%d，bf2 remaining size=%d，bf1 equals bf2=%b\n",
                bf1.remaining(), bf2.remaining(), bf1.equals(bf2));
    }

}
