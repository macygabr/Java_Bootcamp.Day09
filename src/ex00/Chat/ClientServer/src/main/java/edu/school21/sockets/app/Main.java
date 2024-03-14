package edu.school21.sockets.app;

import java.io.*;
import java.net.*;

import java.util.Scanner;

public class Main 
{
    private static Scanner scanner = new Scanner(System.in);
    public static void main( String[] args ) {
        PrintMenu();
    }

    private static void PrintMenu(){
        HeandShake();
        
        String servis = scanner.nextLine();
        Send(servis);
        String login = scanner.nextLine();
        Send(login);
        System.out.print("Enter password:\n> ");
        String password = scanner.nextLine();
        Send(password);
    }

    private static void Send(String str){
        try {
            Socket socket = new Socket("localhost", 8081);
            OutputStream out = socket.getOutputStream();
            out.write(str.getBytes());
            out.flush();
            socket.close(); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void HeandShake(){
        try {
            Socket socket = new Socket("localhost", 8081);
            OutputStream out = socket.getOutputStream();
            out.write("\0".getBytes());
            out.flush();

        
            InputStream in = socket.getInputStream();
            int answer = in.read();
            if(answer == 0) System.out.println("Hello from Server!");
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
