package server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {

    private static final int PORT = 6666;

    public static void main(String[] args){
        try(ServerSocket serverSocket = new ServerSocket(PORT)){
            while (true) {
                new Thread(new ServerThread(serverSocket.accept())).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
