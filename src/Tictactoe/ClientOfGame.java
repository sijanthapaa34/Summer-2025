
package Tictactoe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientOfGame {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ClientOfGame() throws IOException {
        socket = new Socket("127.0.0.1", 6666);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    public void start() {
        MessageReceiver messageReceiver = new MessageReceiver(in);
        messageReceiver.start();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String message = scanner.nextLine();
            out.println(message);
        }
    }

    public static void main(String[] args) throws IOException {
        ClientOfGame client = new ClientOfGame();
        client.start();
    }
}
