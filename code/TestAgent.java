import java.io.*;
import java.net.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class TestAgent {
    private int port;
    private static int sendcount;

    public TestAgent() {
        this.port = 10010;
        TestAgent.sendcount = 0;
    }

    public void AgentStart() {
        try (ServerSocket serverSocket = new ServerSocket(this.port)) {
            System.out.println("TestAgent Started");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Connection Requested.");
                TestAgentReceiveThread st = new TestAgentReceiveThread(socket);
                st.start();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        TestAgent ta = new TestAgent();
        ta.AgentStart();
    }

    private class TestAgentReceiveThread extends Thread {
        private Socket socket;

        public TestAgentReceiveThread(Socket sock) {
            super();
            this.socket = sock;
        }

        public String getServerInfo() throws Exception {
            InetAddress ip = InetAddress.getLocalHost();
            String msg = ip.getHostAddress() + ":" + port;
            return msg;
        }

        public String getTime() throws Exception {
            long time = System.currentTimeMillis();
            SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String msg = dayTime.format(new Date(time));
            return msg;
        }

        public String getSendCount() throws Exception {
            String msg = String.valueOf(sendcount);
            return msg;
        }

        public void sendMessage(PrintWriter writer, String msg) throws Exception {
            writer.println(msg);
            System.out.println("Sent : " + msg);
            writer.flush();
            sendcount++;
        }

        public void run() {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                 PrintWriter writer = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream()), true)) {
                String receivedmsg = "";
                int i = 0;
                while (true) {
                    receivedmsg = reader.readLine();
                    if (receivedmsg != null) {
                        System.out.println(">" + receivedmsg);
                        String sendmsg = "";
                        if (receivedmsg.equals("[disconnect]")) {
                            break;
                        } else if (receivedmsg.equals("[info]")) {
                            sendmsg = this.getServerInfo();
                            sendMessage(writer, sendmsg);
                        } else if (receivedmsg.equals("[time]")) {
                            sendmsg = this.getTime();
                            sendMessage(writer, sendmsg);
                        } else if (receivedmsg.equals("[count]")) {
                            sendmsg = this.getSendCount();
                            sendMessage(writer, sendmsg);
                        } else {
                            sendmsg = "0";
                            sendMessage(writer, sendmsg);
                        }
                    }
                }
                System.out.println("Client is disconnected.");
                this.socket.close();
            } catch (Exception e) {
                System.out.println("catch");
                System.out.println(e.getMessage());
            }
        }
    }
}
