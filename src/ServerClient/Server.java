//package ServerClient;
//
//import java.io.*;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.util.ArrayList;
//import java.util.List;
//
//public class Server {
//    public static List<ServerClientHandler> clientHandlers = new ArrayList<>();
//
//    public static void main(String[] args) throws IOException {
//        ServerSocket serverSocket = new ServerSocket(6666);
//
//        ServerInputHandler inputHandler = new ServerInputHandler();
//        inputHandler.start();
//
////        KeyCommandListener keyListener = new KeyCommandListener();
////        keyListener.start();
//
//        int clientCount = 0;
//        while (true) {
//            Socket socket = serverSocket.accept();
//            clientCount++;
//            ServerClientHandler handler = new ServerClientHandler(socket, "Client" + clientCount);
//            clientHandlers.add(handler);
//            handler.start();
//            System.out.println(handler.getClientName() + " connected.");
//        }
//    }
//
//    public static void broadcastFromServer(String message) {
//        for (ServerClientHandler handler : clientHandlers) {
//            handler.sendMessage("Server: " + message);
//        }
//    }
//}
package ServerClient;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static List<ServerClientHandler> clientHandlers = new ArrayList<>();
    //in file

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("Server started");

        while (true) {
            Socket socket = serverSocket.accept();
            ServerClientHandler handler = new ServerClientHandler(socket);
            handler.start();
        }
    }
}
