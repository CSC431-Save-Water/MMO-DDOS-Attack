
import java.io.*;
import java.net.*;

/**
 * MMORPG Client for Social Engineering Roleplay Team: Save-Water (Eli Manning,
 * Jacob Pugh, Brandon Magana)
 */
public class MMORPGClient {

    // UPDATED: Points to your VM IP as seen in your ifconfig
    private static final String SERVER_ADDRESS = "10.0.101.64";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        System.out.println("Connecting to MMORPG Server at " + SERVER_ADDRESS + "...");

        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); PrintWriter out = new PrintWriter(socket.getOutputStream(), true); BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))) {

            // 1. READ THE "MALICIOUS" ALERT IMMEDIATELY
            // This loop reads every line the server sends until it sees the "Waiting" message
            String serverLine;
            while ((serverLine = in.readLine()) != null) {
                System.out.println(serverLine);
                if (serverLine.contains("Waiting for update confirmation")) {
                    break;
                }
            }

            // 2. SOCIAL ENGINEERING PROMPT
            // Now the user sees the curl command and we wait for them to "login"
            System.out.print("\nEnter Player Name to bypass update: ");
            String playerName = consoleInput.readLine();
            out.println(playerName);

            // 3. BACKGROUND THREAD FOR GAME UPDATES
            // This stays open so the bystander thinks the game is still running
            Thread listenerThread = new Thread(() -> {
                try {
                    String updateMessage;
                    while ((updateMessage = in.readLine()) != null) {
                        if (updateMessage.startsWith("UPDATE:")) {
                            System.out.println(updateMessage.replace("UPDATE:", "Network: "));
                        }
                    }
                } catch (IOException e) {
                    System.out.println("\n[!] Connection to server lost.");
                }
            });
            listenerThread.setDaemon(true);
            listenerThread.start();

            // 4. MAIN GAME LOOP (FAKE)
            System.out.println("Welcome, " + playerName + ". Type 'MOVE:X,Y' to play or 'QUIT' to exit.");
            String userInput;
            while ((userInput = consoleInput.readLine()) != null) {
                if (userInput.equalsIgnoreCase("QUIT")) {
                    break;
                }

                // For the roleplay, we just echo back that they are moving
                if (userInput.startsWith("MOVE:")) {
                    out.println(userInput);
                }
            }

        } catch (ConnectException e) {
            System.err.println("Error: Could not connect to server. Is DirtyMMORPGServer running on " + SERVER_ADDRESS + "?");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
