package ServerClient;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
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

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void start() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("Enter message: ");
                String message = scanner.nextLine();

                if (message.trim().isEmpty()) {
                    continue;
                }

                String formattedMessage = formatMessage(message);
                if (formattedMessage != null) {
                    sendMessage(formattedMessage);
                }
            }
        }
    }
    private String formatMessage(String message) {
        if (message.startsWith("/login") || message.startsWith("/signup") || message.startsWith("/help")) {
            return message;
        }

        if (sessionId == null) {
            System.out.println("You must login first!");
            return null;
        }

        if (message.startsWith("/message")) {
            String[] parts = message.split(";");
            if (parts.length < 4) {
                System.out.println("Format: /message;sessionid;receiver;message");
                return null;
            }
            messageCounter++;
            return "/message;" + sessionId + ";" + messageCounter + ";" + parts[2] + ";" + parts[3];
        }

        if (message.startsWith("/broadcast")) {
            String[] parts = message.split(";", 2);
            if (parts.length < 3) {
                System.out.println("Format: /broadcast;message");
                return null;
            }
            messageCounter++;
            return "/broadcast;" + sessionId + ";" + messageCounter + ";" + parts[2];
        }

        if (message.startsWith("/get_messages")) {
            String[] parts = message.split(";");
            if (parts.length == 1) {
                return "/get_messages;" + sessionId;
            } else if (parts.length == 2) {
                return "/get_messages;" + sessionId + ";" + parts[2];
            } else if (parts.length == 3) {
                return "/get_messages;" + sessionId + ";" + parts[2] + ";" + parts[3];
            }
        }

        if (message.equals("/logout")) {
            return "/logout;" + sessionId;
        }

        // If it's not a recognized command, treat it as a broadcast message
        if (!message.startsWith("/")) {
            messageCounter++;
            return "/broadcast;" + sessionId + ";" + messageCounter + ";" + message;
        }

        return message;
    }

    private void sendMessage(String message) {
        try (Socket socket = new Socket()) {
            // Set connection timeout
            socket.connect(new InetSocketAddress(host, port), 5000);
            socket.setSoTimeout(10000); // Set read timeout

            System.out.println("new connection has established");
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                out.println(message);

                // Read response with timeout and content length handling
                ServerResponse response = readServerResponse(in);
                processResponse(response);

            }

        } catch (IOException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }

    private ServerResponse readServerResponse(BufferedReader in) throws IOException {
        ServerResponse response = new ServerResponse();
        String line;
        int expectedLines = 0;

        while ((line = in.readLine()) != null) {

            // Handle content length header
            if (line.startsWith("CONTENT_LENGTH:")) {
                expectedLines = Integer.parseInt(line.split(":")[1]);
                continue;
            }

            response.lines.add(line);

            // Stop if we reached expected line count
            if (expectedLines > 0 && response.lines.size() >= expectedLines) {
                response.complete = true;
                break;
            }
        }

        return response;
    }

    private void processResponse(ServerResponse response) {
        if (!response.complete) {
            System.out.println("Received incomplete response from server");
        }

        StringBuilder messageHistory = new StringBuilder();

        for (String line : response.lines) {
            if (line.startsWith("LOGIN_SUCCESS;")) {
                String[] parts = line.split(";");
                if (parts.length >= 3) {
                    this.setSessionId(parts[1]);
                    this.setUsername(parts[2]);
                    System.out.println("Username: "+ getUsername() + " Session ID: "+getSessionId());
                }
                continue;
            }

            // Handle status messages
            if (line.startsWith("200;") || line.startsWith("400;") || line.startsWith("401;") ||
                    line.startsWith("404;") || line.startsWith("409;") || line.startsWith("500;")) {
                System.out.println("Server: " + line);
                continue;
            }


            // Collect message history
            if (!line.isEmpty()) {
                messageHistory.append(line).append("\n");
            }
        }

        // Render message history if we have any
        if (!messageHistory.isEmpty()) {
            renderMessageHistory(messageHistory.toString());
        }
    }

    private void renderMessageHistory(String messages) {
        System.out.println("\n+------------------------------------------------+");
        System.out.println("| MESSAGE HISTORY                                ");
        if (username != null) {
            System.out.println("| User: " + username);
        }
        System.out.println("|------------------------------------------------|");

        if (messages.equals("No messages yet.")) {
            System.out.println("| No messages yet. Start chatting!              |");
        } else {
            String[] lines = messages.split("\n");
            for (String line : lines) {
//                if (line.trim().isEmpty()) continue;
                System.out.println("| " + line);
            }
        }

        System.out.println("|------------------------------------------------|\n");
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }

    // Inner class to hold server response data
    private static class ServerResponse {
        List<String> lines = new ArrayList<>();
        boolean complete = false;
    }
}