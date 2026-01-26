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

    public static synchronized void broadcast(String message, PlayerHandler sender) {
        if (PlayerHandler player: players.values()) {
            if (player != sender) {
                player.sendMessage(message);
            }
        }
    }

    public static synchronized void updatePlaterPosition(String playerName, String positionData) {
        String message = "UPDATE: " + playerName + ":" + positionData;
        broadcast(message, players.get(playerName));
    }

    public static synchronized void addPlayer(String playerName, PlayerHandler playerHandler) {
        players.put(playerName, playerHandler);
    }

    public static synchronized void removePlayer(String playerName) {
        players.remove(playerName);
    }
}

class PlayerHandler implements Runnable {
    private Socket socket;
    private PrintWriter out;
    private BufferReader in;
    private String playerName
}