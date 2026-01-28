import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PayloadAttackClient {
    private static final String SERVER_ADDRESS = "10.0.101.84";
    private static final int SERVER_PORT = 12345;
    private static final int WORKER_COUNT = 10; // Active threads in the pool
    private static final int TOTAL_TASKS = 1000; // Total connection attempts to cycle through
    private static final int PAYLOAD_SIZE = 1024 * 1024; // 1MB payload per connection

    public static void main(String[] args) {
        // Use a pool to manage workers
        ExecutorService executor = Executors.newFixedThreadPool(WORKER_COUNT);
        byte[] buffer = new byte[PAYLOAD_SIZE];
        java.util.Arrays.fill(buffer, (byte) 'A');

        System.out.println("Starting pooled simulation with " + WORKER_COUNT + " workers...");

        for (int i = 0; i < TOTAL_TASKS; i++) {
            final int taskId = i;
            executor.submit(() -> {
                try {
                    Socket socket = new Socket();
                    // Set a timeout so a hung server doesn't stall your pool
                    socket.connect(new InetSocketAddress(SERVER_ADDRESS, SERVER_PORT), 2000); 
                    
                    try (BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream())) {
                        out.write(buffer);
                        out.flush();
                        System.out.println("Task " + taskId + ": Payload delivered.");
                    }
                } catch (IOException e) {
                    System.err.println("Task " + taskId + " failed: " + e.getMessage());
                }
            });
        }

        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Simulation complete.");
    }
}