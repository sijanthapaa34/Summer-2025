package Internship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client1 {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    public static void main(String[] args) throws IOException {
        Socket clientSocket = new Socket("127.0.0.1", 6666);
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        Scanner scanner = new Scanner(System.in);


        out.println("hello server");


        while (true) {
            String serverResponse = in.readLine();
            System.out.println("Server: " + serverResponse);
            System.out.print("Client: ");
            String clientMessage = scanner.nextLine();
            out.println(clientMessage);


            if (clientMessage.equalsIgnoreCase("bye")) {
                System.out.println("Chat ended by client.");
                break;
            }
        }


        in.close();
        out.close();
        clientSocket.close();
    }


}
