package nethttp;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutorService;

/**
 * It is an example of a http WebSocket
 * that can be followed to perform common tasks using
 * the Java HTTP Client WebSocket implementation.
 */
public class EchoWebSocket {

    private static final ExecutorService executor = Executors.newFixedThreadPool(3);
    public static void main(String[] args) throws InterruptedException {
        HttpClient client = HttpClient.newBuilder().executor(executor).build();
        WebSocket ws = client.newWebSocketBuilder()
                            .buildAsync(URI.create("ws://demos.kaazing.com/echo"), new EchoListener(executor))
                            .join();
        System.out.println("WebSocket created");
        TimeUnit.MILLISECONDS.sleep(800);
        ws.sendClose(WebSocket.NORMAL_CLOSURE, "OK").thenRun(() -> System.out.println("Sent close")).join();
    }
}
