
package ServerClient;

import java.io.*;
import java.net.Socket;

public class ServerClientHandler extends Thread {
    private final Socket socket;
    private String username;
    private String password;
    public boolean isActive = true;
    private PrintWriter out;
    private BufferedReader in;
    private boolean isLoggedIn = false;
    StringBuilder activeMsg = new StringBuilder("Active Users: ");
    StringBuilder inactiveMsg = new StringBuilder("Inactive Users: ");

    public ServerClientHandler(Socket socket) {
        this.socket = socket;
    }

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public void run() {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println("1 for Login  /2 for Signup ");
            String decision = in.readLine();
            if(decision.equals("2")){
                signupProcess();
            }
            else if (decision.equals("1")) {
                loginProcess();
            }
            else {
                out.println("Invalid choice. Disconnecting.");
                return;
            }
            if(isLoggedIn){
            Server.clientHandlers.add(this);
            messageLoop();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException ignored) {}
        }

    }

    private void messageLoop() throws IOException {
        System.out.print("\033[H\033[2J");
        saveUser();
        String input;
        String destination = "";
        String message = "";
        while ((input = in.readLine()) != null) {
            if (input.equalsIgnoreCase("/logout")) {
                this.isActive = false;
                out.println("You have logged out.");
                saveUser();
                Server.clientHandlers.remove(this);
                break;
            }
            else if (input.equalsIgnoreCase("/users")) {
                sendUser();
            }
            else if (input.startsWith("@")) {
                for(int i = 1; i<input.length(); i++){
                    if(input.charAt(i)== ' ') {
                        destination = input.substring(1, i);
                        message = input.substring(i+1);
                        break;
                    }
                }
                for(ServerClientHandler handler : Server.clientHandlers){
                    if(handler.getUsername().equals(destination)){
                        String dmMessage = "[DM] " + username + " → " + destination + ": " + message;
                        handler.sendMessage(dmMessage);
                        logMessageToFile(dmMessage);
                    }
                }
            } else {
                broadcastToAll(input);
            }
        }
    }

    private void sendUser() {
        StringBuilder activeMsg = new StringBuilder("Active Users: ");
        StringBuilder inactiveMsg = new StringBuilder("Inactive Users: ");

        for (ServerClientHandler handler : Server.clientHandlers) {
            if (handler.isActive) {
                activeMsg.append(handler.getUsername()).append(", ");
            } else {
                inactiveMsg.append(handler.getUsername()).append(", ");
            }
        }

        for (ServerClientHandler handler : Server.clientHandlers) {
            if (handler.isActive) {
                handler.sendMessage(activeMsg.toString());
                handler.sendMessage(inactiveMsg.toString());
            }
        }
    }

    private void loginProcess() throws IOException {
        while (true) {
            out.println("Enter Username: ");
            String name = in.readLine();
            out.println("Enter Password: ");
            String pass = in.readLine();

            boolean success = false;
            for (ServerClientHandler serverClientHandler : Server.clientHandlers) {
                if (name.equals(serverClientHandler.getUsername()) && pass.equals(serverClientHandler.getPassword())) {
                    this.username = name;
                    this.password = pass;
                    out.println("Login successful.");
                    out.println("Welcome back " + username + "!");
                    isLoggedIn= true;
                    success = true;
                    break;
                }
            }
            if (success) {
                break;
            }
            else {
                out.println("Invalid username or password. Try again.");
            }
        }
    }

    private void signupProcess() throws IOException {
        File userFile = new File("users.txt");
        if (!userFile.exists()) {
            userFile.getParentFile().mkdirs();
            userFile.createNewFile();
        }

        out.println("Enter Username: ");

        while (true) {
            String name = in.readLine().trim();

            boolean existsInFile = false;
            try (BufferedReader br = new BufferedReader(new FileReader(userFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(":");
                    if (parts.length >= 2 && parts[0].equalsIgnoreCase(name)) {
                        existsInFile = true;
                        break;
                    }
                }
            }

            if (existsInFile) {
                out.println("Username already exists. Try another username:");
                continue;
            }

//            boolean existsInMemory = false;
//            for (ServerClientHandler handler : Server.clientHandlers) {
//                if (name.equalsIgnoreCase(handler.getUsername())) {
//                    existsInMemory = true;
//                    break;
//                }
//            }
//
//            if (existsInMemory) {
//                out.println("Username already exists (currently logged in). Try another username:");
//                continue;
//            }

            out.println("Enter password: ");
            String firstPassword = in.readLine();

            out.println("Enter the password again: ");
            String finalPassword = in.readLine();

            if (!firstPassword.equals(finalPassword)) {
                out.println("Passwords do not match. Try signing up again.");
                continue;
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(userFile, true))) {
                bw.write(name + ":" + finalPassword);
                bw.newLine();
            }

            this.username = name;
            this.password = finalPassword;
            this.isLoggedIn = true;

            out.println("Signup successful.");
            break;
        }
    }


    private void broadcastToAll(String message) {
        String fullMessage = username + ": " + message;
        logMessageToFile(fullMessage);
        for (ServerClientHandler handler : Server.clientHandlers) {
            if (handler != this) {
                handler.sendMessage(fullMessage);
            }
        }
    }

    private void logMessageToFile(String message) {
        try (FileWriter fw = new FileWriter("History.txt", true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println(message);
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }

    public  void saveUser() {
        activeMsg = new StringBuilder("Active Users: ");
        inactiveMsg = new StringBuilder("Inactive Users: ");

        for (ServerClientHandler handler : Server.clientHandlers) {
            if (handler.isActive) {
                activeMsg.append(handler.getUsername()).append(", ");
            } else {
                inactiveMsg.append(handler.getUsername()).append(", ");
            }
        }

        System.out.print("\033[H\033[2J");
        System.out.println(activeMsg);
        System.out.println(inactiveMsg);


//        try (FileWriter fw = new FileWriter("users.txt", true)) {
//            for(ServerClientHandler clientHandler: Server.clientHandlers)
//            {
//                fw.write("Username: "+clientHandler.getUsername() + " " + "Password: "+ clientHandler.getPassword() + "\n");
//            }
//
//        } catch (IOException e) {
//            System.err.println("Error saving user: " + e.getMessage());
//        }

    }
}