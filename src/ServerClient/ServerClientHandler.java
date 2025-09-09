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
    private static Map<String, Integer> userLastMessageId = new HashMap<>();

    private Map<String, Command> commandMap = new HashMap<>();

    public ServerClientHandler(Socket socket) {
        this.socket = socket;
        registerCommands();
    }

    private void registerCommands() {
        commandMap.put("/signup", parts -> {
            if (parts.length < 3) {
                sendSingleResponse("400;Format: /signup;username;password");
                return;
            }
            String username = parts[1];
            String password = parts[2];
            signupProcess(username, password);
        });

        commandMap.put("/login", parts -> {
            if (parts.length < 3) {
                sendSingleResponse("400;Format: /login;username;password");
                return;
            }
            String username = parts[1];
            String password = parts[2];
            loginProcess(username, password);
        });

        commandMap.put("/message", parts -> {
            if (parts.length < 5) {
                sendSingleResponse("400;Format: /message;sessionId;messageId;receiver;message");
                return;
            }
            String sessionId = parts[1];
            int messageId;
            try {
                messageId = Integer.parseInt(parts[2]);
            } catch (NumberFormatException e) {
                sendSingleResponse("400;Invalid message ID format");
                return;
            }
            String receiver = parts[3];
            String message = parts[4];
            sendDirectMessage(sessionId, messageId, receiver, message);
        });

        commandMap.put("/get_messages", parts -> {
            if (parts.length < 2) {
                sendSingleResponse("400;Format: /get_messages;sessionId;[fromUser];[fromDateTime]");
                return;
            }
            String sessionId = parts[1];
            String fromUser = parts.length >= 3 ? parts[2] : null;
            String fromDateTime = parts.length >= 4 ? parts[3] : null;
            getMessages(sessionId, fromUser, fromDateTime);
        });

        commandMap.put("/broadcast", parts -> {
            if (parts.length < 4) {
                sendSingleResponse("400;Format: /broadcast;sessionId;messageId;message");
                return;
            }
            String sessionId = parts[1];
            int messageId;
            try {
                messageId = Integer.parseInt(parts[2]);
            } catch (NumberFormatException e) {
                sendSingleResponse("400;Invalid message ID format");
                return;
            }
            String message = parts[3];
            broadcastToAll(sessionId, messageId, message);
        });

        commandMap.put("/logout", parts -> {
            if (parts.length < 2) {
                sendSingleResponse("400;Format: /logout;sessionId");
                return;
            }
            String sessionId = parts[1];
            logout(sessionId);
        });

        commandMap.put("/help", parts ->
                sendSingleResponse("200;Available commands: /signup, /login, /message, /broadcast, /logout, /sync, /help"));
    }

    @Override
    public void run() {
        try (
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            this.out = out;
            socket.setSoTimeout(10000);

            String input = in.readLine();
            if (input != null && !input.trim().isEmpty()) {
                System.out.println("Received: " + input);

                // Check for message corruption
                if (isMessageCorrupted(input)) {
                    String corruptionAlert = "ALERT;Message corruption detected: " + input;
                    System.out.println("SERVER ALERT: " + corruptionAlert);
                    sendSingleResponse(corruptionAlert);
                    return;
                }

                handleCommand(input);
            } else {
                sendSingleResponse("400;No command received");
            }

        } catch (IOException e) {
            System.err.println("Error in handler: " + e.getMessage());
        }
    }

    private boolean isMessageCorrupted(String message) {
        // Basic corruption checks
        if (message == null || message.trim().isEmpty()) {
            return true;
        }

        // Check for malformed structure
        String[] parts = message.split(";");
        if (parts.length == 0) {
            return true;
        }

        // Check for suspicious characters that might indicate corruption
        if (message.contains("\0") || message.length() > 10000) {
            return true;
        }

        return false;
    }

    private void handleCommand(String input) throws IOException {
        String[] parts = input.split(";");
        String command = parts[0];

        Command cmd = commandMap.get(command);
        if (cmd != null) {
            cmd.execute(parts);
        } else {
            sendSingleResponse("400;Unknown command: " + command);
        }
    }

    private void sendSingleResponse(String response) {
        out.println("CONTENT_LENGTH:1");
        out.println(response);
    }

    private void sendMultilineResponse(List<String> responses) {
        out.println("CONTENT_LENGTH:" + responses.size());
        for (String response : responses) {
            out.println(response);
        }
    }

    private void logout(String sessionId) {
        String username = sessions.remove(sessionId);
        if (username != null) {
            userLastMessageId.remove(username);
            sendSingleResponse("200;Logged out successfully");
        } else {
            sendSingleResponse("401;Invalid SessionID");
        }
    }

    private void sendDirectMessage(String sessionId, int messageId, String receiver, String message) {
        String senderUsername = sessions.get(sessionId);
        if (senderUsername == null) {
            sendSingleResponse("401;Invalid SessionID");
            return;
        }


        // Check if receiver exists
        if (!userExists(receiver)) {
            sendSingleResponse("404;User " + receiver + " not found");
            return;
        }

        // Track the message ID
        trackMessageId(senderUsername, messageId);

        logMessageToFile(senderUsername, receiver, "DIRECT", message, messageId);
        sendSingleResponse("200;Message " + messageId + " sent to " + receiver);
    }

    private void broadcastToAll(String sessionId, int messageId, String message) {
        String senderUsername = sessions.get(sessionId);
        if (senderUsername == null) {
            sendSingleResponse("401;Invalid SessionID");
            return;
        }

        trackMessageId(senderUsername, messageId);

        logMessageToFile(senderUsername, "", "BROADCAST", message, messageId);
        sendSingleResponse("200;Broadcast message " + messageId + " sent");
    }

    private boolean isValidMessageId(String username, int messageId) {
        Integer lastId = userLastMessageId.get(username);

        if (lastId != null && messageId != lastId + 1) {
            return false;
        }
        return true;
    }

    private void trackMessageId(String username, int messageId) {
        userLastMessageId.put(username, messageId);
    }

    private void loginProcess(String username, String password) throws IOException {
        File userFile = new File("users.txt");
        if (!userFile.exists()) {
            sendSingleResponse("500;User database not found");
            return;
        }

        boolean userExists = false;
        try (BufferedReader br = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length >= 2 && parts[0].equals(username) && parts[1].equals(password)) {
                    userExists = true;
                    break;
                }
            }
        }

        if (userExists) {
            String sessionId = String.valueOf(1000 + new Random().nextInt(9000));
            sessions.put(sessionId, username);
            userLastMessageId.computeIfAbsent(username, k -> 0);

            sendSingleResponse("LOGIN_SUCCESS;" + sessionId + ";" + username);
        } else {
            sendSingleResponse("401;Invalid username or password. Try again.");
        }
    }

    private void signupProcess(String username, String password) throws IOException {
        File userFile = new File("users.txt");
        if (!userFile.exists()) {
            File parentDir = userFile.getParentFile();
            if (parentDir != null) {
                parentDir.mkdirs();
            }
            userFile.createNewFile();
        }

        if (userExists(username)) {
            sendSingleResponse("409;Username already exists. Try another username.");
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(userFile, true))) {
            bw.write(username + ":" + password);
            bw.newLine();
        }

        this.username = username;
        this.password = password;

        sendSingleResponse("200;Signup successful.");
    }

    private boolean userExists(String username) {
        File userFile = new File("users.txt");
        if (!userFile.exists())
            return false;

        try (BufferedReader br = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length >= 2 && parts[0].equalsIgnoreCase(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("Error checking user existence: " + e.getMessage());
        }
        return false;
    }

    private void logMessageToFile(String sender, String receiver, String type, String message, int messageId) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        String formattedMessage = timestamp + "|" + type + "|" + sender + "|" +
                (receiver == null || receiver.isEmpty() ? "" : receiver) + "|" +
                messageId + "|" + message;
        try (FileWriter fw = new FileWriter("History.txt", true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println(formattedMessage);
        } catch (IOException e) {
            System.err.println("Error writing to History.txt: " + e.getMessage());
        }
    }

    private void getMessages(String sessionId, String fromUser, String fromDateTime) {
        String username = sessions.get(sessionId);
        if (username == null) {
            sendSingleResponse("401;Invalid SessionID");
            return;
        }

        File file = new File("History.txt");
        List<String> messages = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime filterDateTime = null;

        // Parse filter date if provided
        if (fromDateTime != null) {
            try {
                filterDateTime = LocalDateTime.parse(fromDateTime, formatter);
            } catch (Exception e) {
                sendSingleResponse("400;Invalid date format. Use yyyy-MM-dd");
                return;
            }
        }

        try {
            List<String> historyMessages = Files.readAllLines(file.toPath());

            for (String message : historyMessages) {
                String[] parts = message.split("\\|", 5);
                if (parts.length < 5) continue;

                String timestamp = parts[0];
                String type = parts[1];
                String sender = parts[2];
                String receiver = parts[3];
                String content = parts[4];

                // Only include messages relevant to the logged-in user
                boolean isRelevant = type.equals("BROADCAST") || sender.equals(username) || receiver.equals(username);
                if (!isRelevant)
                    continue;

                // Filter by user
                if (fromUser != null) {
                    if (!sender.equalsIgnoreCase(fromUser) && !receiver.equalsIgnoreCase(fromUser)) {
                        continue;
                    }
                }

                // Filter by date
                if (filterDateTime != null) {
                    try {
                        LocalDateTime messageDateTime = LocalDateTime.parse(timestamp, formatter);
                    } catch (Exception e) {
                        continue;
                    }
                }

                String displayMessage;
                if(type.equals("BROADCAST")){
                    displayMessage = "[" + timestamp + "] [BROADCAST] " + sender + ": " + content;
                }else{
                    displayMessage = "[" + timestamp + "] " + sender + " -> " + receiver + ": " + content;
                }
                messages.add(displayMessage);
            }

            if (messages.isEmpty()) {
                sendSingleResponse("200;No messages found matching the criteria.");
            } else {
                sendMultilineResponse(messages);
            }

        } catch (IOException e) {
            System.err.println("Error reading message history: " + e.getMessage());
            sendSingleResponse("500;Error loading message history.");
        }
    }
}