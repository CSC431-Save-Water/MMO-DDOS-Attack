import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MMORPGServer {
    private static final int PORT = 12345;
    private static Map<String, PlayerHandler>  players = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        try(ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("MMORPG Server is running on port " + PORT);

            while(true) {
                Socket clientSocket serverSocket.accept();
                PlayerHandler playerHandler = new PlayerHandler(clientSocket);
                new Thread(playerHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class PlayerHandler implements Runnable {

}