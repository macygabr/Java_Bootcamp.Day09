package edu.school21.sockets.app;

import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.Scanner;
import org.apache.commons.cli.*;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;

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
        System.out.print("> ");
        String message = scanner.nextLine();
        if(message.equals("exit")) return;
        else if(message.equals("signUp")) SignUp();
        else if(message.equals("signIn")) SignIn();
        else System.out.println("Wrong command");
    }

    private static void SignUp() throws Exception {
        String clientPackages = "signUp:";
        System.out.print("Enter username:\n> ");
        clientPackages+=scanner.nextLine()+":";
        System.out.print("Enter password:\n> ");
        clientPackages+=scanner.nextLine();
        System.out.println(Send(clientPackages));
    }
    
    private static void SignIn() throws Exception {
        String clientPackages = "signIn:";
        System.out.print("Enter username:\n> ");
        clientPackages+=scanner.nextLine()+":";
        System.out.print("Enter password:\n> ");
        clientPackages+=scanner.nextLine();

        Socket socket = new Socket("localhost", port);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
        out.println(clientPackages);
        
        MessageManager(in, out);
        socket.close();
    }

    private static String Send(String str) throws IOException {
        Socket socket = new Socket("localhost", port);
        PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        out.println(str);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String response = in.readLine();
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

    private static void MessageManager(BufferedReader in, PrintWriter out) throws Exception {
        String answerServer = in.readLine();
        System.out.println(answerServer);
        if(!answerServer.equals("Start messaging")) return;

        String message = "";
        while (!message.equals("exit")) {
            System.out.print("> ");
            message = scanner.nextLine();
            out.println(message);
            System.out.println(in.readLine());
        }
    }
}
