package per.owisho.learn.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;

public class SocketExample {
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        Socket socket = new Socket("localhost", 9999);
        writeDataToSocket(socket);
        readDataFromSocket(socket);
        socket.close();
    }

    private static void writeDataToSocket(Socket socket) throws IOException {
        OutputStream out = socket.getOutputStream();
        out.write("some data\n".getBytes());
        out.flush();
    }

    private static void readDataFromSocket(Socket socket) throws IOException, InterruptedException, URISyntaxException {
//        HttpClient client = HttpClient.newBuilder().build();
//        HttpResponse<String> resp = client.send(HttpRequest.newBuilder(new URI("https://jenkov.com")).GET().build(), HttpResponse.BodyHandlers.ofString(Charset.forName("UTF-8")));
//        System.out.println(resp.body());
        InputStream in = socket.getInputStream();
        int data = in.read();
        while (data != -1 && (char) data != '\n') {
            System.out.print((char) data);
            data = in.read();
        }
    }
}
