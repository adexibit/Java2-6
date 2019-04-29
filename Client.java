import java.io.*;
import java.net.InetAddress;

import java.net.Socket;

public class Client {

    public static void main(String[] args) throws IOException {
        int port = 2555;
        InetAddress inetAddress = InetAddress.getLocalHost();
        Socket clientSocket = new Socket(inetAddress, port);
        InputStream inputStream = clientSocket.getInputStream();
        OutputStream outputStream = clientSocket.getOutputStream();
        DataInputStream dis = new DataInputStream(inputStream);
        DataOutputStream dos = new DataOutputStream(outputStream);
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);


        Thread recieve = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        System.out.println("Server написал : " + dis.readUTF());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }


        });
        Thread send = new Thread((Runnable) () -> {
            while (true) {
                try {
                    dos.writeUTF(br.readLine());
                    dos.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        recieve.start();
        send.start();
    }


}
