package per.owisho.learn.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketExample {

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(9999);
        while (true) {
            Socket socket = serverSocket.accept();
            InputStream in = socket.getInputStream();
            int b = in.read();
            while (b != -1 && (char) b != '\n') {
                System.out.print((char) b);
                b = in.read();
            }
            System.out.println();

            OutputStream os = socket.getOutputStream();
            os.write("test\n".getBytes());
            os.flush();
        }
    }

}
