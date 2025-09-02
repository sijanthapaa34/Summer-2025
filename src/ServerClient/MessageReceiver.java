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
            while ((msg = in.readLine()) != null) {
                if (msg.startsWith("LOGIN_SUCCESS;")) {
                    String[] parts = msg.split(";");
                    if (parts.length >= 3) {
                        this.client.setSessionId(parts[1]);
                        this.client.setUsername(parts[2]);
                        System.out.println("✅ Logged in as " + parts[2]);
                    }
                    continue;
                }


                renderMessage(msg);
            }
        } catch (IOException e) {
            System.out.println("Disconnected from server.");
        }
    }

    private void renderMessage(String msg) {
        System.out.print("\033[H\033[2J"); // clear screen
        System.out.println("+------------------------------------------------+");

        String[] lines = msg.split("\n");

        for (String line : lines) {
            if (line.startsWith(client.getUsername() + ":")) {
                // replace with Me:
                System.out.println("|            Me: " + line.substring(client.getUsername().length() + 1).trim());
            } else {
                System.out.println("| " + line);
            }
        }

        System.out.println("|------------------------------------------------|");
    }
}
