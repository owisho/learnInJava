package per.owisho.learn.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SocketChannelExample {

    public static void main(String[] args) throws IOException {
        readDataFromSocketChannel();
    }

    private static void readDataFromSocketChannel() throws IOException {

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("jenkov.com", 80));
        ByteBuffer buf = ByteBuffer.allocate(48);
        int bytesRead = socketChannel.read(buf);
        while (bytesRead != -1){
            buf.flip();
            while(buf.hasRemaining()){
                System.out.println((char) buf.get());
            }
            buf.clear();
            bytesRead = socketChannel.read(buf);
        }
        socketChannel.close();

    }

}
