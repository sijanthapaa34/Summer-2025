
package Tictactoe;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static Tictactoe.AsciiXO.*;

public class Server {
    public static List<ServerClientHandler> clientHandlers = new ArrayList<>();
    public static String[][] board = new String[3][3];
    public static ServerClientHandler currentTurn;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("Server started... Waiting for players.");

        while (true) {
            Socket socket = serverSocket.accept();

            if (clientHandlers.size() >= 2) {
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println("Server busy. Game is full.");
                socket.close();
                continue;
            }

            ServerClientHandler handler = new ServerClientHandler(socket);
            clientHandlers.add(handler);
            handler.start();

            if (clientHandlers.size() == 2) {
                assignSymbols();
            }
        }
    }

//    public static synchronized void initBoard() {
//        for (int i = 0; i < 3; i++)
//            for (int j = 0; j < 3; j++)
//                board[i][j] = " ";
//    }
    private static void assignSymbols() {
        Random random = new Random();
        int first = random.nextInt(2);
        int second = 1 - first;

        ServerClientHandler player1 = clientHandlers.get(first);
        ServerClientHandler player2 = clientHandlers.get(second);

        player1.setSymbol("X");
        player2.setSymbol("O");

        currentTurn = player1;

        player1.sendMessage("You are X. Your turn first!");
        player2.sendMessage("You are O. Wait for your turn.");
        broadcast(printBoard());
    }

    public static void broadcast(String message) {
        for (ServerClientHandler handler : clientHandlers) {
            handler.sendMessage(message);
        }
    }

    public static String printBoard() {
        int length = X_SHAPE.length;
        StringBuilder sb = new StringBuilder();
        sb.append("_____________________________________________");
        sb.append("\n");
        for (int row = 0; row < 3; row++) {
            for(int line = 0;line<length;line++) {
                for (int col = 0; col < 3; col++) {
                    String[] shape;
                    String symbol = board[row][col];

                    if ("X".equals(symbol)) {
                        shape = AsciiXO.X_SHAPE;
                    } else if ("O".equals(symbol)) {
                        shape = AsciiXO.O_SHAPE;
                    } else {
                        shape = AsciiXO.EMPTY_SHAPE;
                    }
                    sb.append(shape[line]);
                }
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}