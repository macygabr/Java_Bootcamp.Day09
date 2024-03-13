package edu.school21.sockets.app;

import edu.school21.sockets.config.SocketsApplicationConfig;
import edu.school21.sockets.server.Server;
import org.apache.commons.cli.*;




public class Main {
    public static void main( String[] args ) {
        try {
            ParseArgs(args);
            new Server();

        } catch (ParseException e) {
            System.err.println("Ошибка при парсинге аргументов: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }
    
    private static void ParseArgs(String[] args) throws ParseException {
        Options options = new Options();
        options.addOption("p", "port=8081", true, "port");
    
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;
    
        cmd = parser.parse(options, args);
        String arg1Value = cmd.getOptionValue("p", "=");
    }
}