package ServerClient;

import java.io.BufferedReader;
import java.io.IOException;

public class MessageReceiver extends Thread {
    private final BufferedReader in;
    private final Client client;

    public MessageReceiver(BufferedReader in, Client client) {
        this.in = in;
        this.client = client;
    }

    public void run() {
        try {
            String msg;
            StringBuilder messageHistory = new StringBuilder();
            boolean receivedHistory = false;

            while ((msg = in.readLine()) != null) {
                // Handle login success message
                if (msg.startsWith("LOGIN_SUCCESS;")) {
                    String[] parts = msg.split(";");
                    if (parts.length >= 3) {
                        this.client.setSessionId(parts[1]);
                        this.client.setUsername(parts[2]);
                        System.out.println("Session: " + parts[1] );

                        // Render history if we have any
                        if (!messageHistory.isEmpty()) {
                            renderMessage(messageHistory.toString());
                            messageHistory.setLength(0);
                        }
                    }
                    continue;
                }

                // Handle status messages
                if (msg.startsWith("200;") || msg.startsWith("400;") || msg.startsWith("401;")) {
                    System.out.println("Server: " + msg);
                    continue;
                }

                // Collect message history (everything else)
                if (!msg.trim().isEmpty()) {
                    messageHistory.append(msg).append("\n");
                    receivedHistory = true;
                }
            }

            // If we received history but no login success, render it
            if (receivedHistory && messageHistory.length() > 0) {
                renderMessage(messageHistory.toString());
            }

        } catch (IOException e) {
            System.out.println("Disconnected from server.");
        }
    }

    private void renderMessage(String msg) {
        // Don't clear screen immediately - show the message properly first
        if (msg.trim().isEmpty() || msg.equals("No messages yet.")) {
            System.out.println("+------------------------------------------------+");
            System.out.println("| No messages yet.                              |");
            System.out.println("|------------------------------------------------|");
            return;
        }

        System.out.println("+------------------------------------------------+");
        System.out.println("| MESSAGE HISTORY                                |");
        System.out.println("|------------------------------------------------|");

        String[] lines = msg.split("\n");
        for (String line : lines) {
            if (line.trim().isEmpty()) continue;

            // Format the line to fit in the box
            String formattedLine;
            if (client.getUsername() != null && line.contains(client.getUsername())) {
                // Replace username with "You" for better readability
                if (line.startsWith("[BROADCAST] " + client.getUsername() + ":")) {
                    formattedLine = line.replace("[BROADCAST] " + client.getUsername() + ":", "[BROADCAST] You:");
                } else if (line.contains(client.getUsername() + " ->")) {
                    formattedLine = line.replace(client.getUsername() + " ->", "You ->");
                } else if (line.contains("-> " + client.getUsername())) {
                    formattedLine = line.replace("-> " + client.getUsername(), "-> You");
                } else {
                    formattedLine = line;
                }
            } else {
                formattedLine = line;
            }

            // Truncate long lines and add padding
            if (formattedLine.length() > 46) {
                formattedLine = formattedLine.substring(0, 43) + "...";
            }

            System.out.println("| " + formattedLine + String.format("%" + (46 - formattedLine.length()) + "s", "") + " |");
        }

        System.out.println("|------------------------------------------------|");

        // Add a small delay to ensure the message is visible before the next prompt
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}