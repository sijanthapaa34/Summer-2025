
package Tictactoe;

import java.io.BufferedReader;
import java.io.IOException;

public class MessageReceiver extends Thread {
    private BufferedReader in;

    public MessageReceiver(BufferedReader in) { this.in = in; }

    public void run() {
        try {
            System.out.print("\033[H\033[2J");
            String response;
            while ((response = in.readLine()) != null) {
                System.out.println(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}