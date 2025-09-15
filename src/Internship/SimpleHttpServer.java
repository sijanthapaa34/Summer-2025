package Internship;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class SimpleHttpServer {


    public static void main(String[] args) throws IOException {
        String name = "";
        String email = "";
        String gender = "";
        List<String> skills = new ArrayList<>();
        int port = 8080;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server started on port:" + port);

        Socket client = serverSocket.accept();

        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

        String requestLine = in.readLine();
        System.out.println("Request: " + requestLine);
//        GET /? HTTP/1.1
//        name=Sijan+Thapa & email=sijanmgr034%40gmail.com & gender=Male&skills=Java
        Map<String, List<String>> params = new HashMap<>();

        if(requestLine != null){
            String[] parts = requestLine.split(" ");
            String query = parts[1].substring(2);

            for (String pair : query.split("&")) {
                String[] kv = pair.split("=");
                String key = URLDecoder.decode(kv[0], StandardCharsets.UTF_8);
                String value = URLDecoder.decode(kv[1], StandardCharsets.UTF_8);
                if (!params.containsKey(key)) {
                    params.put(key, new ArrayList<>());
                }
                params.get(key).add(value);
            }
        }
        name= params.get("name").getFirst();
        email = params.get("email").getFirst();
        gender = params.get("gender").getFirst();
        skills = params.get("skills");


        // Build HTML template with placeholders
        String bodyTemplate = """
        <!DOCTYPE html>
        <html>
        <head><title>Form Submitted</title></head>
        <body>
            <h2>Thank you!</h2>
            <p>Your form has been submitted successfully.</p>
            <p>name: {name}</p>
            <p>email: {email}</p>
            <p>gender: {gender}</p>
            <p>skills: {skills}</p>
        </body>
        </html>
        """;

// Replace placeholders with actual values
        String body = bodyTemplate
                .replace("{name}", name)
                .replace("{email}", email)
                .replace("{gender}", gender)
                .replace("{skills}",String.join(", ", skills));

        // Send HTTP response
        String response = "HTTP/1.1 200 OK\r\n" +
                "Content-Type: text/html\r\n" +
                "Content-Length: " + body.length() + "\r\n" +
                "\r\n" +
                body;

        out.write(response);
        out.flush();

        client.close();
        serverSocket.close();
    }
}
