package nethttp;

import java.net.http.WebSocket;
import java.net.http.WebSocket.Listener;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutorService;

/**
 * A listener for WebSocket communication
 */
public final class EchoListener implements WebSocket.Listener {
    private ExecutorService executor;

    public EchoListener(ExecutorService executor) { this.executor = executor; }

    @Override
    public void onOpen(WebSocket webSocket) {
        System.out.println("CONNECTED");
        webSocket.sendText("This is a message", true);
        Listener.super.onOpen(webSocket);
    }

    @Override
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
        System.out.println("Receiving Message -->");
        System.out.format("onText() received a data: %s", data);

        if(!webSocket.isOutputClosed())
            webSocket.sendText("This message should echoed back..", true);
        
        return Listener.super.onText(webSocket, data, last);
    }

    @Override
    public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
        System.out.println("CLOSING");
        System.out.format("Closed with status: %d, and reason: %s", statusCode, reason);
        executor.shutdown();
        return Listener.super.onClose(webSocket, statusCode, reason);
    }
}