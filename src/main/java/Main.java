import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        ServerSocket serverSocket;
        Socket clientSocket = null;
        int port = 6379;
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setReuseAddress(true);
            clientSocket = serverSocket.accept();
            InputStream in = clientSocket.getInputStream();
            byte[] bytes = new byte[1024];
            int read = in.read(bytes);
            while (read != -1) {
                System.out.println("接收到的消息" + new String(bytes, 0, read));
                OutputStream outputStream = clientSocket.getOutputStream();
                outputStream.write("+PONG\r\n".getBytes());
            }

        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        } finally {
            try {
                if (clientSocket != null) {
                    clientSocket.close();
                }
            } catch (IOException e) {
                System.out.println("IOException: " + e.getMessage());
            }
        }
    }
}
