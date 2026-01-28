import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class MMORPGClientScanner {
    // Scans local network for IP's that have an open port '12345'
    // Also tests connection

    public static void main(String[] args) {
        String subnet = "192.168.1.";
        int port = 12345;
        int timeout = 200; // milliseconds

        for (int i = 1; i < 255; i++) {
            String host = subnet + i;
            try (Socket socket = new Socket()) {
                socket.connect(new InetSocketAddress(host, port), timeout);
                System.out.println("Found open port at: " + host + ":" + port);

                // Test connection by sending a simple message
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                out.println("TEST_CONNECTION");
                String response = in.readLine();
                System.out.println("Response from " + host + ": " + response);

            } catch (IOException e) {
                // Port is closed or host is unreachable
            }
        }
    }
}