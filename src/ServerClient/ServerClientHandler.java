package ServerClient;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ServerClientHandler extends Thread {

    private final Socket socket;
    private String username, password;
    private PrintWriter out;

    private static Map<String, String> sessions = new HashMap<>();
    private Map<String, Command> commandMap = new HashMap<>();

    public ServerClientHandler(Socket socket) {
        this.socket = socket;
        registerCommands();
    }

    private void registerCommands() {
        commandMap.put("/signup", parts -> {
            if (parts.length < 3) {
                out.println("400;Format: /signup;username;password");
                return;
            }
            signupProcess(parts[1], parts[2]);
        });

        commandMap.put("/login", parts -> {
            if (parts.length < 3) {
                out.println("400;Format: /login;username;password");
                return;
            }
            loginProcess(parts[1], parts[2]);
        });

        commandMap.put("/message", parts -> {
            if (parts.length < 4) {
                out.println("400;Format: /message;sessionId;receiver;message");
                return;
            }
            sendDirectMessage(parts[1], parts[2], parts[3]);
        });

        commandMap.put("/broadcast", parts -> {
            if (parts.length < 3) {
                out.println("400;Format: /broadcast;sessionId;message");
                return;
            }
            broadcastToAll(parts[1], parts[2]);
        });

        commandMap.put("/logout", parts -> {
            if (parts.length < 2) {
                out.println("400;Format: /logout;sessionId");
                return;
            }
            logout(parts[1]);
        });

        commandMap.put("/help", parts ->
                out.println("200;/signup;/login;/message;/broadcast;/logout;/help"));
    }

    @Override
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
            System.err.println("Error in handler: " + e.getMessage());
        }
    }

    private void handleCommand(String input) throws IOException {
        String[] parts = input.split(";");
        System.out.println(Arrays.toString(parts));
        String command = parts[0];

        Command cmd = commandMap.get(command);
        if (cmd != null) {
            cmd.execute(parts);
        } else {
            out.println("400;Unknown command");
        }
    }

    private void logout(String sessionId) {
        if (sessions.remove(sessionId) != null) {
            out.println("200;Logged out");
        } else {
            out.println("401;Invalid SessionID");
        }
    }

    private void sendDirectMessage(String sessionId, String receiver, String message) {
        String senderUsername = sessions.get(sessionId);
        if (senderUsername == null) {
            out.println("401;Invalid SessionID");
            return;
        }

        logMessageToFile(senderUsername, receiver, "DIRECT", message);
    }

    private void broadcastToAll(String sessionId, String message) {
        String senderUsername = sessions.get(sessionId);
        if (senderUsername == null) {
            out.println("401;Invalid SessionID");
            return;
        }

        logMessageToFile(senderUsername, "", "BROADCAST", message);
    }


    private void loginProcess(String username, String password) throws IOException {
        File userFile = new File("users.txt");
        if (!userFile.exists()) {
            out.println("File not found");
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
            String sessionId = String.valueOf(1000 + new Random().nextInt(9000));
            sessions.put(sessionId, username);

            out.println(getMessageHistory(sessionId));
            out.println("LOGIN_SUCCESS;" + sessionId + ";" + username);

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

    private void logMessageToFile(String sender, String receiver, String type, String message) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        String formattedMessage = String.format("%s|%s|%s|%s|%s", timestamp, type, sender,
                receiver != null ? receiver : "", message);

        try (FileWriter fw = new FileWriter("History.txt", true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println(formattedMessage);
        } catch (IOException e) {
            System.err.println("Error writing to History.txt: " + e.getMessage());
        }
    }

    private String getMessageHistory(String sessionId) {
        String username = sessions.get(sessionId);
        if (username == null) return "401;Invalid SessionID";

        StringBuilder sb = new StringBuilder();
        File file = new File("History.txt");

        try {
            List<String> historyMessage = Files.readAllLines(file.toPath());

            for (String message : historyMessage) {
                String[] parts = message.split("\\|", 5);
                if (parts.length < 5) continue;

                String type = parts[1];
                String sender = parts[2];
                String receiver = parts[3];

                if (type.equals("BROADCAST") || sender.equals(username) || receiver.equals(username)) {
                    sb.append(message).append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString().trim();
    }


}
