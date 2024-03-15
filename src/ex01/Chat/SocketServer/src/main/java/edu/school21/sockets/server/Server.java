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
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@Component("Server")
public class Server {
    
    @Autowired
    @Qualifier("UsersServiceImpl")
    private UsersService usersService;
    
    private String SignUpMode;

    public void run(int port) {
        while(true){
        try (ServerSocket serverSocket = new ServerSocket(port);
            Socket clientSocket = serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String request = in.readLine();
            if("Handshake".equals(request)) {
                out.println("Hello from Server!");
            } else {
                System.out.println(request);
                out.println(MenuManager(request));
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    }
    
    private String MenuManager(String request) {
        String answer="Error command";
        try {
            String[] clientPackages = request.split(":");
            answer = SignUpDataBase(clientPackages, answer);
            answer = SignInDataBase(clientPackages, answer);
        } catch (Exception e) {
            answer = "\033[31m"+ e.getMessage() + "\033[0m";
        }
        return answer;
    }
    
    private String SignUpDataBase(String[] clientPackages, String answer) throws Exception {
        if(clientPackages[0].equals("signUp") && clientPackages.length == 3) {
            System.out.println(clientPackages[1] + " " + clientPackages[2]);
             usersService.signUp(clientPackages[1], clientPackages[2]);
             return "Successful!";
        }
        return answer;
    }

    private String SignInDataBase(String[] clientPackages, String answer) throws Exception {
        if(clientPackages[0].equals("signIn") && clientPackages.length == 3) {
             if(usersService.signIn(clientPackages[1], clientPackages[2]));
             return "Start messaging";
        }
        return answer;
    }
}