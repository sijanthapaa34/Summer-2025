
package ServerClient;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private boolean isLoggedIn = false;

    public Client() {
        connectToServer();
    }

    private void connectToServer() {
        while (true) {
            try {
                System.out.println("Trying to connect to server...");
                socket = new Socket("127.0.0.1", 6666);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                System.out.println("Connected to server!");
                break;
            } catch (IOException e) {
                System.out.println("Connection failed. Retrying in 3 seconds...");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ignored) {}
            }
        }
    }

    public void start() {
        try (Scanner scanner = new Scanner(System.in);
             BufferedReader reader = in;
             PrintWriter writer = out) {

            MessageReceiver messageReceiver = new MessageReceiver(reader, this);
            messageReceiver.start();

            while (true) {
                String message = scanner.nextLine();

                if (!isLoggedIn) {
                    messageReceiver.historyMessage.add(message);
                } else {
                    messageReceiver.historyMessage.add("Me: " + message);
                }

                try {
                    writer.println(message);
                } catch (Exception e) {
                    System.out.println("Lost connection. Reconnecting...");
                    connectToServer();
                    start();
                    break;
                }

                if (message.equalsIgnoreCase("bye")) {
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println("Error in client: " + e.getMessage());
        }
    }

    public void setLoggedIn(boolean status) {
        this.isLoggedIn = status;
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        client.start();
    }
}
