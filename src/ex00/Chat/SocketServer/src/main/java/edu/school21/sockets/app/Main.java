package edu.school21.sockets.app;

import edu.school21.sockets.config.SocketsApplicationConfig;
import edu.school21.sockets.server.Server;
import edu.school21.sockets.services.UsersService;

import org.apache.commons.cli.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;



public class Main {
    private static ApplicationContext context = new AnnotationConfigApplicationContext(SocketsApplicationConfig.class);
    private static Scanner scanner = new Scanner(System.in);
    public static void main( String[] args ) {
        try (ServerSocket serverSocket = new ServerSocket(8081)) {
                // ParseArgs(args);
                // StartServer();
            while (true) {
                Socket socket = serverSocket.accept();
                int answer = socket.getInputStream().read();
                for (int i = 0; i < 1000; i++) {;}
                System.out.println(answer);
                if(answer == 0) {
                    OutputStream outputStream = socket.getOutputStream();
                    outputStream.write("\0".getBytes());
                    outputStream.flush();
                }
                socket.close();
            }
        } catch (IOException e) {
            System.err.println("Error in the server: " + e.getMessage());
        // } catch (ParseException e) {
            // System.err.println("Ошибка при парсинге аргументов: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
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