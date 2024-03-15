package edu.school21.sockets.app;

import edu.school21.sockets.config.SocketsApplicationConfig;
import edu.school21.sockets.server.Server;

import org.apache.commons.cli.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

public class Main {
    private static ApplicationContext context = new AnnotationConfigApplicationContext(SocketsApplicationConfig.class);
    private static Scanner scanner = new Scanner(System.in);
    public static void main( String[] args ) {
        try {
            int port = ParseArgs(args);
            ConnectServer(port);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    private static int ParseArgs(String[] args) throws Exception {
        Options options = new Options();
        options.addOption("p", "port", true, "port");
    
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;
    
        cmd = parser.parse(options, args);
        String arg1Value = cmd.getOptionValue("p", "=");
        return Integer.parseInt(arg1Value);
    }

    private static void ConnectServer(int port) {
        Server server = context.getBean(Server.class);
        server.run(port);
    }
}