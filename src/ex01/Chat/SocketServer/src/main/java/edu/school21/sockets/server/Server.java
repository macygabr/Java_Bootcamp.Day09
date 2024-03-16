package edu.school21.sockets.server;
import edu.school21.sockets.app.*;

import edu.school21.sockets.config.SocketsApplicationConfig;
import edu.school21.sockets.repositories.UsersRepository;
import edu.school21.sockets.services.UsersService;
import edu.school21.sockets.services.UsersServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.net.ssl.*;
import javax.net.ssl.SSLEngineResult.HandshakeStatus;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;

@Component("Server")
public class Server {

    @Autowired
    @Qualifier("UsersServiceImpl")
    private UsersService usersService;

    public void run(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)){
            while (true) {
                Socket clientSocket = serverSocket.accept(); 
                System.out.println("\033[32mClient connected: " + clientSocket + "\033[0m");
                
                ClientHandler clientHandler = new ClientHandler(clientSocket,usersService);
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


class ClientHandler extends Thread {
    private Socket clientSocket;
    private UsersService usersService;
    private static List<Socket> clients = new ArrayList<>();

    public ClientHandler(Socket socket, UsersService usersService) {
        this.clientSocket = socket;
        this.usersService = usersService;
    }

    @Override
    public void run() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

            String message = input.readLine();
            Handshake(message, output);
            MessageManager(message, input, output);
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void Handshake(String request, PrintWriter output) {
        if(request.equals("Handshake")) output.println("Successful!");
    }

    private void MessageManager(String request, BufferedReader input ,PrintWriter output) {
        try {
            String[] clientPackages = request.split(":");
            if(clientPackages[0].equals("signUp")) SignUpDataBase(clientPackages,output);
            if(clientPackages[0].equals("signIn")) SignInDataBase(clientPackages, input ,output);
        } catch (Exception e) {
            output.println("\033[31m"+ e.getMessage() + "\033[0m");
        }
    }

    private void SignUpDataBase(String[] clientPackages, PrintWriter output) throws Exception {
        if(clientPackages.length != 3) throw new Exception("Bad request");
        usersService.signUp(clientPackages[1], clientPackages[2]);
        output.println("Successful!");
    }
    
    private void SignInDataBase(String[] clientPackages, BufferedReader input, PrintWriter output) throws Exception {
        if(clientPackages.length != 3) throw new Exception("Bad request");
        if(usersService.signIn(clientPackages[1], clientPackages[2]));
        else throw new Exception("User not found");
        output.println("Start messaging");

        clients.add(clientSocket);
        String message = "";
        while (!message.equals("exit")) {
            message = input.readLine();
            for(Socket socket : clients) {
                PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
                out.println(": " + message);
            }
        }
    }

    private void SignInClient(String[] clientPackages, PrintWriter output) throws Exception {
        if(clientPackages.length != 2) throw new Exception("Bad request");
        output.println("Successful!");
    }
}