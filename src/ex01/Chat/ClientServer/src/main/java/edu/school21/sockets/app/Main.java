package edu.school21.sockets.app;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import org.apache.commons.cli.*;

public class Main 
{
    private static Scanner scanner = new Scanner(System.in);
    private static int port;
    public static void main( String[] args ) {
        try {
            port = ParseArgs(args);
            Handshake();
            PrintMenu();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    private static int ParseArgs(String[] args) throws Exception {
        Options options = new Options();
        options.addOption("sp", "server-port", true, "port");
    
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;
        cmd = parser.parse(options, args);
        String arg1Value = cmd.getOptionValue("sp", "=");
        return Integer.parseInt(arg1Value);
    }

    private static void PrintMenu() throws Exception {
        String clientPackages = "";
        System.out.print("> ");
        clientPackages+=scanner.nextLine()+":";
        System.out.print("Enter username:\n> ");
        clientPackages+=scanner.nextLine()+":";
        System.out.print("Enter password:\n> ");
        clientPackages+=scanner.nextLine();
        String answerServer = Send(clientPackages);
        System.out.println(answerServer);
        // if(answerServer.equals("Start messaging"))StartMessage();
    }

    private static String Send(String str) throws IOException {
        Socket socket = new Socket("localhost", port);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        out.println(str);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String response = in.readLine();
        if(response.equals("200")) response = "";
        socket.close();
        return response;
    }

    private static void Handshake() throws Exception {
        try {
        System.out.println(Send("Handshake"));
        } catch (Exception e) {
            throw new RuntimeException("Server not found");
        }
    }

    private static void StartMessage() throws Exception {
        String message = "";
        try {
            while(message.equals("exit")) {
                System.out.print("> ");
                message = scanner.nextLine();
                String answerServer = Send(message);
                System.out.println(answerServer);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
