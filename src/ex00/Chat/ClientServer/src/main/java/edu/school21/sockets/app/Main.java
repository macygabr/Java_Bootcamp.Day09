package edu.school21.sockets.app;

import java.io.*;
import java.net.*;

import java.util.Scanner;

public class Main 
{
    private static Scanner scanner = new Scanner(System.in);
    public static void main( String[] args ) {
        try {
            Handshake();
            PrintMenu();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void PrintMenu() throws Exception {
        String clientPackages = "";
        System.out.print("> ");
        clientPackages+=scanner.nextLine()+":";
        System.out.print("Enter username:\n> ");
        clientPackages+=scanner.nextLine()+":";
        System.out.print("Enter password:\n> ");
        clientPackages+=scanner.nextLine();
        System.out.println(Send(clientPackages));
    }

    private static String Send(String str) throws IOException {
        Socket socket = new Socket("localhost", 8081);
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
}
