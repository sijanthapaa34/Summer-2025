//package ServerClient;
//
//import java.io.*;
//import java.net.Socket;
//import java.util.ArrayList;
//import java.util.List;
//
//public class ServerClientHandler extends Thread {
//    private final Socket socket;
//    private final String clientName;
//    private PrintWriter out;
//    private BufferedReader in;
//    public static List<String> serverHistory = new ArrayList<>();
//
//
//    public ServerClientHandler(Socket socket, String clientName) {
//        this.socket = socket;
//        this.clientName = clientName;
//    }
//
//    public String getClientName() {
//        return clientName;
//    }
//
//    public void sendMessage(String message) {
//        out.println(message);
//    }
//    //yo chai nagarni
//
//    public void run() {
//        try {
//            out = new PrintWriter(socket.getOutputStream(), true);
//            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//
//            out.println("Welcome " + clientName + "!");
//
//            String input;
//            while ((input = in.readLine()) != null) {
//                String message = clientName + ": " + input;
//                serverHistory.add(message);
//            }
//           // printMessageBox(0);
//        } catch (IOException e) {
//            System.out.println("Connection error with " + clientName);
//        }
//            try {
//                in.close();
//                out.close();
//                socket.close();
//            } catch (IOException ignored) {}
//
//    }
//
////
////
////
////
////    public void printMessageBox(int starting) {
////        System.out.print("\033[H\033[2J");
////        System.out.println("+------------------------------------------------+");
////        for(int i = starting; i< serverHistory.size();i++) {
////            System.out.println("| " +serverHistory.get(i));
////        }
////        System.out.println("|------------------------------------------------|");
////    }
//}
//
package ServerClient;

import java.io.*;
import java.net.Socket;

public class ServerClientHandler extends Thread {
    private final Socket socket;
    private String username;
    private String password;
    private PrintWriter out;
    private BufferedReader in;
    private boolean isLoggedIn = false;

    public ServerClientHandler(Socket socket) {
        this.socket = socket;
    }

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public void run() {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out.println("1 for Login  /2 for Signup ");
            String decision = in.readLine();
            if(decision.equals("2")){
                signupProcess();
            }
            else if (decision.equals("1")) {
                loginProcess();
            }
            else {
                out.println("Invalid choice. Disconnecting.");
                return;
            }
            if(isLoggedIn){
            Server.clientHandlers.add(this);
            messageLoop();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException ignored) {}
        }

    }

    private void messageLoop() throws IOException {
        String input;
        String destination = "";
        String message = "";
        while ((input = in.readLine()) != null) {
            if(input.startsWith("@")) {
                for(int i = 1; i<input.length(); i++){
                    if(input.charAt(i)== ' ') {
                        destination = input.substring(1, i);
                        message = input.substring(i+1);
                        break;
                    }
                }
                for(ServerClientHandler handler : Server.clientHandlers){
                    if(handler.getUsername().equals(destination)){
                        String dmMessage = "[DM] " + username + " → " + destination + ": " + message;
                        handler.sendMessage(dmMessage);
                        logMessageToFile(dmMessage);
                    }
                }
            }
            else{
                broadcastToAll(input);
            }
        }
    }

    private void loginProcess() throws IOException {
        while (true) {
            out.println("Enter Username: ");
            String name = in.readLine();
            out.println("Enter Password: ");
            String pass = in.readLine();

            boolean success = false;
            for (ServerClientHandler serverClientHandler : Server.clientHandlers) {
                if (name.equals(serverClientHandler.getUsername()) && pass.equals(serverClientHandler.getPassword())) {
                    this.username = name;
                    this.password = pass;
                    out.println("Login successful.");
                    out.println("Welcome back " + username + "!");
                    isLoggedIn= true;
                    success = true;
                    break;
                }
            }
            if (success) {
                break;
            }
            else {
                out.println("Invalid username or password. Try again.");
            }
        }
    }

    private void signupProcess() throws IOException {
        //for username
        out.println("Enter Username: ");
        while (true) {
            String name = in.readLine();
            boolean exist = false;
            for(ServerClientHandler serverClientHandler :Server.clientHandlers){
                if(name.equals(serverClientHandler.getUsername())){
                    out.println("Username already exist: Try another username:");
                    exist = true;
                    break;
                }
            }
            if (!exist) {
                out.println("Enter password: ");
                String firstPassword = in.readLine();
                out.println("Enter the password again: ");
                String finalPassword = in.readLine();
                if(firstPassword.equals(finalPassword)){
                    this.username = name;
                    this.password = finalPassword;
                    isLoggedIn= true;
                    out.println("Login successful.");
                    out.println("Welcome "+username);
                    break;
                }else{
                    out.println("password does not match");
                }
            }
        }
    }

    private void broadcastToAll(String message) {
        String fullMessage = username + ": " + message;
        logMessageToFile(fullMessage);
        for (ServerClientHandler handler : Server.clientHandlers) {
            if (handler != this) {
                handler.sendMessage(fullMessage);
            }
        }
    }

    private void logMessageToFile(String message) {
        try (FileWriter fw = new FileWriter("History.txt", true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println(message);
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }
}