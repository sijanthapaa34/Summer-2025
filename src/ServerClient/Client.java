package ServerClient;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private final String host = "127.0.0.1";
    private final int port = 6666;
    private String username;
    private String sessionId = null;
    private int messageCounter = 0;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void start() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("Enter message: ");
                String message = scanner.nextLine();

                if (message.equalsIgnoreCase("bye")) {
                    System.out.println("Exiting...");
                    break;
                }
                messageCounter++;

                String formattedMessage;
                if (message.startsWith("/login") || message.startsWith("/signup")) {
                    formattedMessage = message;
                } else {
                    if (sessionId == null) {
                        System.out.println("You must login first!");
                        continue;
                    }
                    formattedMessage = message + ";" + sessionId + ";" + messageCounter;
                }

                sendMessage(formattedMessage);
            }
        }
    }

    private void sendMessage(String message) {
        try (Socket socket = new Socket(host, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.println(message);
            out.println(message);

            MessageReceiver receiver = new MessageReceiver(in, this);
            receiver.start();
            receiver.join();

        } catch (IOException | InterruptedException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }

}
