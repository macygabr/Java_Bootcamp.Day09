package edu.school21.sockets.server;
import edu.school21.sockets.app.*;

import edu.school21.sockets.config.SocketsApplicationConfig;
import edu.school21.sockets.repositories.UsersRepository;
import edu.school21.sockets.services.UsersService;
import edu.school21.sockets.services.UsersServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@Component("Server")
public class Server {
    
    @Autowired
    @Qualifier("UsersServiceImpl")
    private UsersService usersService;
    Scanner scanner = new Scanner(System.in);

    public Server() {
    }

    public void run() {
        String command = scanner.nextLine();
        switch (command) {
            case "signUp":
                signUp();
                break;
            default:
                throw new RuntimeException("Unknown command: " + command);
        }
    }

    private void signUp() {

    }
}