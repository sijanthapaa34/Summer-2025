
package Tictactoe;

import java.io.*;
import java.net.Socket;

import static Tictactoe.Server.currentTurn;


public class ServerClientHandler extends Thread {
    private final Socket socket;
    private PrintWriter out;
    private String symbol;

    public ServerClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public void run() {
        try (
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            this.out = out;
            String input;
            while ((input = in.readLine()) != null) {
                handleCommand(input);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleCommand(String input) {
        if (currentTurn != this) {
            out.println("Not your turn!");
            return;
        }

        int numInput;
        try {
            numInput = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            out.println("Invalid input. Enter 1-9.");
            return;
        }

        if (numInput < 1 || numInput > 9) {
            out.println("Invalid input. Enter 1-9.");
            return;
        }

        int row = (numInput - 1) / 3;
        int column = (numInput - 1) % 3;

        if (Server.board[row][column] != null) {
            out.println("Invalid move. Spot already taken.");
            return;
        }

        Server.board[row][column] = symbol;

        Server.broadcast("Player " + symbol + " moved at position " + numInput);

        Server.broadcast(Server.printBoard());

        if(checkWin()){
            Server.broadcast("Player " + symbol + " wins!");
//            making the board empty after game
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    Server.board[i][j] = null;
                }
            }
            currentTurn = null;
        }else{
//            changing the client of the game
            if (currentTurn == Server.clientHandlers.get(0)) {
                currentTurn = Server.clientHandlers.get(1);
            } else {
                currentTurn = Server.clientHandlers.get(0);
            }
            currentTurn.sendMessage("Your turn!");
        }
    }

    private boolean checkWin() {
        String s = this.symbol;
        //row
        for (int i = 0; i<3;i++){
            if (Server.board[i][0]!=null && Server.board[i][0].equals(s)
            && Server.board[i][1]!=null && Server.board[i][1].equals(s)
            && Server.board[i][2]!=null && Server.board[i][2].equals(s)){
                return true;
            }
        }
        //column
        for (int j = 0; j<3;j++){
            if (Server.board[0][j]!=null && Server.board[0][j].equals(s)
            && Server.board[1][j]!=null && Server.board[1][j].equals(s)
            && Server.board[2][j]!=null && Server.board[2][j].equals(s)){
                return true;
            }
        }

        //right diagonal
        if (Server.board[0][0]!=null && Server.board[0][0].equals(s)
            && Server.board[1][1]!=null && Server.board[1][1].equals(s)
            && Server.board[2][2]!=null && Server.board[2][2].equals(s)) {
            return true;
        }

        //left diagonal
        if (Server.board[0][2]!=null && Server.board[0][2].equals(s)
            && Server.board[1][1]!=null && Server.board[1][1].equals(s)
            && Server.board[2][0]!=null && Server.board[2][0].equals(s)) {
            return true;
        }
        return false;
    }
}