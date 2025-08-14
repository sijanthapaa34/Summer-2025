
package ServerClient;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server {
    public static List<ServerClientHandler> clientHandlers = new ArrayList<>();
//    public static Map<String, String> registeredUsers = new HashMap<>();

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