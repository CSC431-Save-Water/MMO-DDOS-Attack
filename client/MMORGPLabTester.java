import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MMORGPLabTester {
    private static final String SERVER_ADDRESS = "192.168.1.50";
    private static final int SERVER_PORT = 12345;
    private static final int TOTAL_CONNECTIONS = 1000;

    public static void main(String[] args) {
        // Using a ThreadPool to manage the client-side attack threads
        ExecutorService executor = Executors.newFixedThreadPool(100);

        for (int i = 0; i < TOTAL_CONNECTIONS; i++) {
            final int clientID = i;
            executor.submit(() -> {
                try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                    // 1. Read the server's initial prompt (Enter your player name:)
                    in.readLine(); 

                    // 2. Automatically provide a name to get past the NullPointerException
                    out.println("Tester_Bot_" + clientID);

                    // 3. Keep the connection open and occasionally send data
                    while (true) {
                        out.println("MOVE:x=" + clientID + ",y=" + clientID);
                        Thread.sleep(5000); // Wait 5 seconds to keep the thread occupied
                    }

                } catch (Exception e) {
                    System.err.println("Client " + clientID + " disconnected: " + e.getMessage());
                }
            });
        }
    }
}