package nethttp;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.URLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;

public class NetDemo {
    public static void main(String[] args) throws IOException {
        URLs();
        readFromURL();
        readFromURLConnection();
        networkInterfaces();
    }

    /**
     * Demonstrates all URL properties
     * @throws MalformedURLException
     */
    private static void URLs() throws MalformedURLException {
        // URL url = new URL("http://baeldung.com/a-guide-to-java-sockets");
        URL base = new URL("http://baeldung.com");
        // URL url = new URL(base, "a-guide-to-java-sockets");
        URL url = new URL("http://baeldung.com/articles?topic=java&version=8");

        System.out.format("%s: %s\n", "Relative URL", url.toString());
        System.out.format("%s: %s\n", "Protocol", url.getProtocol());
        System.out.format("%s: %s\n", "Host", url.getHost());
        System.out.format("%s: %d\n", "Port", url.getPort());
        System.out.format("%s: %s\n", "Default Port: ", url.getDefaultPort());
        System.out.format("%s: %s\n", "Query", url.getQuery());
        System.out.format("%s: %s\n", "Authority", url.getAuthority());
        System.out.format("%s: %s\n", "User Info", url.getUserInfo());

        URL fileURL = new URL("http://baeldung.com/guidelines.txt");
        System.out.format("%s: %s\n", "File: ", fileURL.getFile());        
    }

    /**
     * Reads from a URL using java.io streams
     * @throws IOException
     */
    private static void readFromURL() throws IOException {
        URL url = new URL("http://www.example.com");
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        String line;
        while((line = br.readLine()) != null)
            System.out.println(line);
        br.close();
    }

    /**
     * Reads from URL by opening a connection
     * @throws IOException
     */
    private static void readFromURLConnection() throws IOException {
        URL url = new URL("http://www.example.com");
        URLConnection conn = url.openConnection();
        conn.connect();
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while((line = br.readLine()) != null)
            System.out.println(line);
        br.close();
    }

    /**
     * Network interfaces MAC address etc.
     * @throws IOException
     */
    private static void networkInterfaces() throws IOException {
        // get MAC address i.e. NIC card
        // InetAddress localhost = InetAddress.getLocalHost();
        InetAddress localhost = InetAddress.getByName(" 192.168.0.6");
        NetworkInterface ni = NetworkInterface.getByInetAddress(localhost);
        byte[] hardwareAddress = ni.getHardwareAddress();
        String[] hexadecimal = new String[hardwareAddress.length];
        for (int i = 0; i < hardwareAddress.length; i++) 
            hexadecimal[i] = String.format("%02X", hardwareAddress[i]);

        String macAddress = String.join("-", hexadecimal);
        System.out.println("MAC address: " + macAddress);

        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface nif = networkInterfaces.nextElement();
            hardwareAddress = nif.getHardwareAddress();
            if (hardwareAddress != null) {
                String[] hexadecimalFormat = new String[hardwareAddress.length];
                for (int i = 0; i < hardwareAddress.length; i++) {
                    hexadecimalFormat[i] = String.format("%02X", hardwareAddress[i]);
                }
                System.out.println(String.join("-", hexadecimalFormat));
            }
        }
    }
}