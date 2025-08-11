//package ServerClient;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class MessageReceiver extends Thread {
//    private final BufferedReader in;
//    private List<String> historyMessage = new ArrayList<>();
//
//    public MessageReceiver(BufferedReader in) {
//        this.in = in;
//    }
//
//    public void run() {
//        String msg;
//        try {
//            while ((msg = in.readLine()) != null) {
//                historyMessage.add(msg);
//                printMessageBox();
//            }
//        } catch (IOException e) {
//            System.out.println("Disconnected from server.");
//        }
//    }
//    private void printMessageBox() {
//        System.out.print("\033[H\033[2J");
//        System.out.println("+------------------------------------------------+");
//        for(String message: historyMessage) {
//            System.out.println("| " + message);
//        }
//        System.out.println("|------------------------------------------------|");
//    }
//}
//
package ServerClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MessageReceiver extends Thread {
    private final BufferedReader in;
    private final Client client;
    public List<String> historyMessage = new ArrayList<>();

    public MessageReceiver(BufferedReader in, Client client) {
        this.in = in;
        this.client = client;
    }

    public void run() {
        String msg;
        try {
            while ((msg = in.readLine()) != null) {
                if(msg.equals("Login successful.")){
                    client.setLoggedIn(true);
                }
                historyMessage.add(msg);
                printMessageBox();
            }
        } catch (IOException e) {
            System.out.println("Disconnected from server.");
        }
    }

    private void printMessageBox() {
        System.out.print("\033[H\033[2J");
        System.out.println("+------------------------------------------------+");
        for (String message : historyMessage) {
            if (message.startsWith("Me: ")){
                System.out.println("|       "+message);
            }else {
                System.out.println("| " + message);
            }
        }
        System.out.println("|------------------------------------------------|");
    }
}
