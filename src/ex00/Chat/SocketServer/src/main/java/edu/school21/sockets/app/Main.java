package edu.school21.sockets.app;

import edu.school21.sockets.config.SocketsApplicationConfig;
import edu.school21.sockets.server.Server;
import edu.school21.sockets.services.UsersService;

import org.apache.commons.cli.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;



public class Main {
    private static ApplicationContext context = new AnnotationConfigApplicationContext(SocketsApplicationConfig.class);
    private static Scanner scanner = new Scanner(System.in);
    public static void main( String[] args ) {
        // ParseArgs(args);
        ConnectServer();
    }
    
    private static void ParseArgs(String[] args) throws ParseException {
        Options options = new Options();
        options.addOption("p", "port", true, "port");
    
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;
    
        cmd = parser.parse(options, args);
        String arg1Value = cmd.getOptionValue("p", "=");
        // System.out.println(arg1Value);
    }

    private static void ConnectServer() {
        Server server = context.getBean(Server.class);
        server.run();
    }
}