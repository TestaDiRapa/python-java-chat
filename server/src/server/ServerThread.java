package server;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class ServerThread implements Runnable {

    private Socket socket;
    private static int n=0; //number of client connected
    private static int id=0; //id client


    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try(PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8))) {
            n++;
            id++;
            String msg = "";
            System.out.println("ESTABLISHED CONNESSION");
            while (msg != null) {
                try {
                    msg = reader.readLine();
                } catch(SocketException e){
                    System.out.println("CLIENT " + id+" OFF");
                    n--;
                    if(n==0)
                      System.exit(0);
                    break;
                }
                System.out.println("Client "+id+" : " + msg);
                String response = "ECHO: " + msg;
                writer.write(response);
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
