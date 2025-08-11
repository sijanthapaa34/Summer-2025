//package ServerClient;
//
//import java.io.*;
//import java.net.Socket;
//import java.util.Scanner;
//
//public class Client {
//    private Socket socket;
//    private BufferedReader in;
//    private PrintWriter out;
//
//    public Client() throws IOException {
//        socket = new Socket("127.0.0.1", 6666);
//        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//        out = new PrintWriter(socket.getOutputStream(), true);
//    }
//
//    public void start() {
//        new MessageReceiver(in).start();
//        Scanner scanner = new Scanner(System.in);
//        out.println("Hello Server!");
//        while (true) {
//            String message = scanner.nextLine();
//            out.println(message);//yo chai server ko ma
//            if (message.equalsIgnoreCase("bye")) {
//                break;
//            }
//        }
//
//        try {
//            scanner.close();
//            in.close();
//            out.close();
//            socket.close();
//        } catch (IOException ignored) {}
//    }
//
//    public static void main(String[] args) throws IOException {
//        Client client = new Client();
//        client.start();
//    }
//}
package ServerClient;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private boolean isLoggedIn = false;

    public Client() throws IOException {
        socket = new Socket("127.0.0.1", 6666);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    public void start() {
        MessageReceiver messageReceiver =new MessageReceiver(in, this);
        messageReceiver.start();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String message = scanner.nextLine();
            if(isLoggedIn) {
                messageReceiver.historyMessage.add("Me: " + message);
            }
            out.println(message);
            if (message.equalsIgnoreCase("bye")) {
                break;
            }
        }
        try {
            scanner.close();
            in.close();
            out.close();
            socket.close();
        } catch (IOException ignored) {}
    }

    public void setLoggedIn(boolean status) {
        this.isLoggedIn = status;
    }
    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.start();
    }
}
