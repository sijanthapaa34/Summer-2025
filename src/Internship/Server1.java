package Internship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server1 {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        ServerSocket serverSocket = new ServerSocket(6666);
        Socket clientSocket = serverSocket.accept();
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        while (true) {
            String clientMessage = in.readLine();
            System.out.println("Client: " + clientMessage);


            System.out.print("Server: ");
            String serverMessage = scanner.nextLine();
            out.println(serverMessage);


            if (serverMessage.equalsIgnoreCase("bye")) {
                System.out.println("Chat ended by server.");
                break;
            }
        }
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();


    }
}
