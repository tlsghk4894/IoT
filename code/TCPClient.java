import java.io.*;
import java.net.*;

public class TCPClient {
    public static void main(String[] args) throws IOException {
        String serverIP = "localhost";
        int serverPort = 10010;
        Socket clienSocket = new Socket(serverIP, serverPort);

        BufferedReader reader = new BufferedReader(new InputStreamReader(clienSocket.getInputStream()));
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(clienSocket.getOutputStream()));

        String sendMsg = "Hello Server,";
        System.out.println("To Server:" + sendMsg);
        writer.write(sendMsg + "\n");
        writer.flush();

        String recvMsg = reader.readLine();
        System.out.println("From Server: " + recvMsg);

        clienSocket.close();
        System.out.println("Server disconnected");
    }
}
