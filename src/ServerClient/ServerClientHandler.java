
package ServerClient;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class ServerClientHandler extends Thread {
    private final Socket socket;
    private String username, password;
    public boolean isActive = true;
    private PrintWriter out;

    private static Map<String, String> sessions = new HashMap<>();
    private Map<String, Command> commandMap = new HashMap<>();

    public ServerClientHandler(Socket socket) {
        this.socket = socket;
        registerCommands();
    }

    public String getUsername() {
        return username;
    }

    private void registerCommands() {

        commandMap.put("/signup", new Command() {
            @Override
            public void execute(String[] parts) throws IOException {
                if (parts.length < 3) {
                    out.println("400;Format: /signup;username;password");
                    return;
                }
                signupProcess(parts[1], parts[2]);
            }
        });

        commandMap.put("/login", new Command() {
            @Override
            public void execute(String[] parts) throws IOException {
                if (parts.length < 3) {
                    out.println("400;Format: /login;username;password");
                    return;
                }
                loginProcess(parts[1], parts[2]);
            }
        });

        commandMap.put("/users", new Command() {
            @Override
            public void execute(String[] parts) {
                sendUser();
            }
        });

        commandMap.put("/message", new Command() {
            @Override
            public void execute(String[] parts) {
                if (parts.length < 4) {
                    out.println("400;Format: /message;sessionId;receiver;message");
                    return;
                }
                sendDirectMessage(parts[1], parts[2], parts[3]);
            }
        });

        commandMap.put("/broadcast", new Command() {
            @Override
            public void execute(String[] parts) {
                if (parts.length < 3) {
                    out.println("400;Format: /broadcast;sessionId;message");
                    return;
                }
                broadcastToAll(parts[1], parts[2]);
            }
        });

        commandMap.put("/logout", new Command() {
            @Override
            public void execute(String[] parts) {
                if (parts.length < 2) {
                    out.println("400;Format: /logout;sessionId");
                    return;
                }
                logout(parts[1]);
            }
        });

        commandMap.put("/help", new Command() {
            @Override
            public void execute(String[] parts) {
                out.println("200;/users;/login;/signup;/message;/logout;/broadcast;/help");
            }
        });
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

    private void handleCommand(String input) throws IOException {
        String[] parts = input.split(";");
        String command = parts[0];

        Command cmd = commandMap.get(command);
        if (cmd != null) {
            cmd.execute(parts);
        } else {
            out.println("400;Unknown command");
        }
    }

    private void logout(String sessionId) {
        sessions.remove(sessionId);
        Server.clientHandlers.remove(this);
        out.println("200;Logged out");

    }

    private void broadcastToAll(String sessionId, String message) {
        String senderUsername = sessions.get(sessionId);
        if (senderUsername == null) {
            out.println("401;Invalid SessionID");
            return;
        }
        String fullMessage = senderUsername + ": " + message;
        logMessageToFile(fullMessage);

        for (ServerClientHandler handler : Server.clientHandlers) {
            if (handler != this && handler.isActive) {
                handler.sendMessage(fullMessage);
            }
        }
    }


    private void sendDirectMessage(String sessionId, String receiver, String message) {
        String senderUsername = sessions.get(sessionId);
        if (senderUsername == null) {
            out.println("401;Invalid SessionID");
            return;
        }

        String fullMessage = senderUsername + " [DM]: " + message;

        boolean found = false;
        for (ServerClientHandler handler : Server.clientHandlers) {
            if (handler.getUsername() != null && handler.getUsername().equals(receiver)) {
                handler.sendMessage(fullMessage);
                logMessageToFile(fullMessage);
                found = true;
                break;
            }
        }

        if (!found) {
            out.println("404;User not found or not online");
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

    private void loginProcess(String username, String password) throws IOException {
        File userFile = new File("users.txt");
        if (!userFile.exists()) {
            out.println("FIle not found");
            return;
        }

        boolean userExists = false;
        try (BufferedReader br = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts[0].equals(username) && parts[1].equals(password)) {
                    userExists = true;
                    break;
                }
            }
        }

        if (userExists) {
            this.username = username;
            this.password = password;

            if (!Server.clientHandlers.contains(this)) {
                Server.clientHandlers.add(this);
            }

            Random random = new Random();
            String sessionId = String.valueOf(1000 + random.nextInt(9000));
            sessions.put(sessionId, username);

            out.println("Login successful.");
            out.println("Welcome back " + username + "!");
            out.println("200;" + sessionId);
        } else {
            out.println("Invalid username or password. Try again.");
        }
    }

    private void signupProcess(String username, String password) throws IOException {
        File userFile = new File("users.txt");
        if (!userFile.exists()) {
            userFile.getParentFile().mkdirs();
            userFile.createNewFile();
        }
        boolean existsInFile = false;
        try (BufferedReader br = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length >= 2 && parts[0].equalsIgnoreCase(username)) {
                    existsInFile = true;
                    break;
                }
            }
        }

        if (existsInFile) {
            out.println("Username already exists. Try another username:");
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(userFile, true))) {
            bw.write(username + ":" + password);
            bw.newLine();
        }

        this.username = username;
        this.password = password;

        out.println("Signup successful.");
    }

    private void logMessageToFile(String message) {
        try (FileWriter fw = new FileWriter("History.txt", true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println(message);
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }
}