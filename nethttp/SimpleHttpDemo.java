package nethttp;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.concurrent.ExecutorService;
import java.net.Authenticator;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.io.IOException;

public class SimpleHttpDemo {

    // POST: https://postman-echo.com/post
    // GET: https://postman-echo.com/get
    // Basic authentication: https://postman-echo.com/basic-auth

    private static String PATH = "";
    private static HttpRequest request = null;

    // An Authenticator is an object that negotiates credentials (HTTP authentication) for a connection
    private static Authenticator authenticator = new Authenticator() { 
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(
                "postman", 
                "password".toCharArray());
        }
    };
    

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException, ExecutionException {

        // synchronous calls
        // simpleGetSyncWithoutBody();
        // simplePostSyncWithoutBody();
        // simplePostSyncWithTextBody();
        // simplePostSyncWithByteBody();
        // simplePostSyncWithFileBody();
        // postSyncWithBasicAuth();
        // simpleGetSyncWithMultiRequest();

        // asynchronous calls
        asynchronousGetRequest();
        // simplePostAsyncWithTextBody();
        // simpleGetAsyncWithExecutorPool();

        // completeExceptionally();
    }

    /**
     * Simple GET request to make synchronous call without body content. It includes the following:
     * - version
     * - headers
     * - timeout
     * - proxy setting
     * - redirection policy
     * - cookie policy
     * - HTTP authentication
     * Note: send(): Synchronous calls block until response comes back.
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     */
    private static void simpleGetSyncWithoutBody() throws URISyntaxException, IOException, InterruptedException {
        
        PATH = "https://postman-echo.com/get";

        request = HttpRequest.newBuilder()
                            .uri(new URI(PATH))      // path i.e. URL
                            .version(Version.HTTP_2) // http version
                            .headers("key1", "value1", "key2", "value2") // all headers at once
                            .header("key3", "value3") // each header at once
                            .header("key4", "value4")
                            .timeout(Duration.of(10, ChronoUnit.SECONDS)) // set request timeout
                            .GET() // without content i.e. no body
                            .build();


        HttpResponse<String> response = HttpClient.newBuilder()
                            .proxy(ProxySelector.getDefault()) // setting proxy
                            .followRedirects(Redirect.ALWAYS)  // can redirect the request to the new URI automatically if we set the appropriate redirect policy.
                            .authenticator(authenticator)      // HTTP authentication
                            .cookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_NONE)) // cookie policy
                            .build()
                            .send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("\nHTTP GET synchronous call without body");
        System.out.println("--------------------------------------");
        System.out.println("Response Code: " + response.statusCode());
        System.out.println("Response Body: " + response.body());
    }

    /**
     * Simple POST request to make synchronous call without body content. It includes the following:
     * - version
     * - headers
     * - timeout
     * - proxy setting
     * - redirection policy
     * - cookie policy
     * - HTTP authentication
     * Note: send(): Synchronous calls block until response comes back.
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     */
    private static void simplePostSyncWithoutBody() throws URISyntaxException, IOException, InterruptedException {
        
        PATH = "https://postman-echo.com/post";

        request = HttpRequest.newBuilder()
                            .uri(new URI(PATH))      // path i.e. URL
                            .version(Version.HTTP_2) // http version
                            .headers("Content-Type", "text/plain;charset=UTF-8") // all headers at once
                            .timeout(Duration.of(10, ChronoUnit.SECONDS)) // set request timeout
                            .POST(HttpRequest.BodyPublishers.noBody()) // without content i.e. no body
                            .build();

        HttpResponse<String> response = HttpClient.newBuilder()
                            .proxy(ProxySelector.getDefault()) // setting proxy
                            .followRedirects(Redirect.ALWAYS)  // can redirect the request to the new URI automatically if we set the appropriate redirect policy.
                            .authenticator(authenticator)      // HTTP authentication
                            .cookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_NONE)) // cookie policy
                            .build()
                            .send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("\nHTTP POST synchronous call without body");
        System.out.println("---------------------------------------");
        System.out.println("Response Code: " + response.statusCode());
        System.out.println("Response Body: " + response.body());
    }

    /**
     * Simple POST request to make synchronous call with text body content and test basic authentication. It includes the following:
     * - version
     * - headers
     * - timeout
     * - proxy setting
     * - redirection policy
     * - cookie policy
     * - HTTP authentication
     * Note: send(): Synchronous calls block until response comes back.
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     */
    private static void postSyncWithBasicAuth() throws URISyntaxException, IOException, InterruptedException {
        
        PATH = "https://postman-echo.com/basic-auth";

        request = HttpRequest.newBuilder()
                            .uri(new URI(PATH))      // path i.e. URL
                            .version(Version.HTTP_2) // http version
                            .headers("Content-Type", "text/plain;charset=UTF-8") // all headers at once
                            .timeout(Duration.of(10, ChronoUnit.SECONDS)) // set request timeout
                            .POST(HttpRequest.BodyPublishers.ofString("Sample request body")) // with text content i.e. body
                            .build();

        HttpResponse<String> response = HttpClient.newBuilder()
                            .proxy(ProxySelector.getDefault()) // setting proxy
                            .followRedirects(Redirect.ALWAYS)  // can redirect the request to the new URI automatically if we set the appropriate redirect policy.
                            .authenticator(authenticator)      // HTTP authentication
                            .cookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_NONE)) // cookie policy
                            .build()
                            .send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("\nHTTP POST synchronous call with text body and test basic authentication");
        System.out.println("-------------------------------------------------------------------------");                    
        System.out.println("Response Code: " + response.statusCode());
        System.out.println("Response Body: " + response.body());
    }

    /**
     * Simple POST request to make synchronous call with text body content. It includes the following:
     * - version
     * - headers
     * - timeout
     * - proxy setting
     * - redirection policy
     * - cookie policy
     * - HTTP authentication
     * Note: send(): Synchronous calls block until response comes back.
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     */
    private static void simplePostSyncWithTextBody() throws URISyntaxException, IOException, InterruptedException {
        
        PATH = "https://postman-echo.com/post";

        request = HttpRequest.newBuilder()
                            .uri(new URI(PATH))      // path i.e. URL
                            .version(Version.HTTP_2) // http version
                            .headers("Content-Type", "text/plain;charset=UTF-8") // all headers at once
                            .timeout(Duration.of(10, ChronoUnit.SECONDS)) // set request timeout
                            .POST(HttpRequest.BodyPublishers.ofString("Sample request body")) // with text content i.e. body
                            .build();

        HttpResponse<String> response = HttpClient.newBuilder()
                            .proxy(ProxySelector.getDefault()) // setting proxy
                            .followRedirects(Redirect.ALWAYS)  // can redirect the request to the new URI automatically if we set the appropriate redirect policy.
                            .authenticator(authenticator)      // HTTP authentication
                            .cookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_NONE)) // cookie policy
                            .build()
                            .send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("\nHTTP POST synchronous call with text body");
        System.out.println("-----------------------------------------");                    
        System.out.println("Response Code: " + response.statusCode());
        System.out.println("Response Body: " + response.body());
    }


    /**
     * Simple POST request to make synchronous call with bytes body content. It includes the following:
     * - version
     * - headers
     * - timeout
     * - proxy setting
     * - redirection policy
     * - cookie policy
     * - HTTP authentication
     * Note: send(): Synchronous calls block until response comes back.
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     */
    private static void simplePostSyncWithByteBody() throws URISyntaxException, IOException, InterruptedException {
        
        PATH = "https://postman-echo.com/post";

        // with byte content
        byte[] data = "Sample request body in bytes".getBytes();

        request = HttpRequest.newBuilder()
                            .uri(new URI(PATH))      // path i.e. URL
                            .version(Version.HTTP_2) // http version
                            .headers("Content-Type", "text/plain;charset=UTF-8") // all headers at once
                            .timeout(Duration.of(10, ChronoUnit.SECONDS)) // set request timeout
                            .POST(HttpRequest.BodyPublishers.ofByteArray(data)) // with byte content i.e. body
                         // .POST(HttpRequest.BodyPublishers.ofInputStream(new ByteArrayInputStream(data))) // send input stream
                            .build();

        HttpResponse<String> response = HttpClient.newBuilder()
                            .proxy(ProxySelector.getDefault()) // setting proxy
                            .followRedirects(Redirect.ALWAYS)  // can redirect the request to the new URI automatically if we set the appropriate redirect policy.
                            .authenticator(authenticator)      // HTTP authentication
                            .cookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_NONE)) // cookie policy
                            .build()
                            .send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("\nHTTP POST synchronous call with bytes body");
        System.out.println("------------------------------------------");
        System.out.println("Response Code: " + response.statusCode());
        System.out.println("Response Body: " + response.body());
    }

    /**
     * Simple POST request to make synchronous call with file body content. It includes the following:
     * - version
     * - headers
     * - timeout
     * - proxy setting
     * - redirection policy
     * - cookie policy
     * - HTTP authentication
     * Note: send(): Synchronous calls block until response comes back.
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     */
    private static void simplePostSyncWithFileBody() throws URISyntaxException, IOException, InterruptedException {
        
        PATH = "https://postman-echo.com/post";

        request = HttpRequest.newBuilder()
                            .uri(new URI(PATH))      // path i.e. URL
                            .version(Version.HTTP_2) // http version
                            .headers("Content-Type", "text/plain;charset=UTF-8") // all headers at once
                            .timeout(Duration.of(10, ChronoUnit.SECONDS)) // set request timeout
                            .POST(HttpRequest.BodyPublishers.ofFile(Paths.get("nethttp/http_stack.png"))) // with file i.e. body
                            .build();

        HttpResponse<String> response = HttpClient.newBuilder()
                            .proxy(ProxySelector.getDefault()) // setting proxy
                            .followRedirects(Redirect.ALWAYS)  // can redirect the request to the new URI automatically if we set the appropriate redirect policy.
                            .authenticator(authenticator)      // HTTP authentication
                            .cookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_NONE)) // cookie policy
                            .build()
                            .send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("\nHTTP POST synchronous call with file body");
        System.out.println("-----------------------------------------");
        System.out.println("Response Code: " + response.statusCode());
        System.out.println("Response Body: " + response.body());
    }

    /**
     * Simple asynchronous GET request
     * @throws URISyntaxException
     */
    public static void asynchronousGetRequest() throws URISyntaxException {
        HttpClient client = HttpClient.newHttpClient();
        URI httpURI = new URI("http://jsonplaceholder.typicode.com/posts/1");
        HttpRequest request = HttpRequest.newBuilder(httpURI)
            .version(HttpClient.Version.HTTP_2)
            .build();
        CompletableFuture<Void> futureResponse = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .thenAccept(resp -> {
                System.out.println("\nHTTP GET Asynchronous call");
                System.out.println("-----------------------------------------");
                System.out.println("Got pushed response " + resp.uri());
                System.out.println("Response statuscode: " + resp.statusCode());
                System.out.println("Response body: " + resp.body());
            });
        System.out.println("futureResponse" + futureResponse);
    }

    /**
     * Simple POST request to make Asynchronous call with text body content. It includes the following:
     * - version
     * - headers
     * - timeout
     * - proxy setting
     * - redirection policy
     * - cookie policy
     * - HTTP authentication
     * Note: sendAsync(): Asynchronous call, doesn't wait for the response and non-blocking.
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private static void simplePostAsyncWithTextBody() throws URISyntaxException, IOException, InterruptedException, ExecutionException {
        
        PATH = "https://postman-echo.com/post";

        request = HttpRequest.newBuilder()
                            .uri(new URI(PATH))      // path i.e. URL
                            .version(Version.HTTP_2) // http version
                            .headers("Content-Type", "text/plain;charset=UTF-8") // all headers at once
                            .timeout(Duration.of(10, ChronoUnit.SECONDS)) // set request timeout
                            .POST(HttpRequest.BodyPublishers.ofString("Sample request body")) // with text content i.e. body
                            .build();

        CompletableFuture<HttpResponse<String>> response = HttpClient.newBuilder()
                            .proxy(ProxySelector.getDefault()) // setting proxy
                            .followRedirects(Redirect.ALWAYS)  // can redirect the request to the new URI automatically if we set the appropriate redirect policy.
                            .authenticator(authenticator)      // HTTP authentication
                            .cookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_NONE)) // cookie policy
                            .build()
                            .sendAsync(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("\nHTTP POST Asynchronous call with text body");
        System.out.println("--------------------------------------------");                    
        System.out.println("Response Code: " + response.get().statusCode());
        response.whenComplete((res, t) -> System.out.println("Response Body: " + res.toString()));
    }

    /**
     * Simple GET request to make Asynchronous call with executor pool. It includes the following:
     * - version
     * - headers
     * - timeout
     * - proxy setting
     * - redirection policy
     * - cookie policy
     * - HTTP authentication
     * Note: sendAsync(): Asynchronous call, doesn't wait for the response and non-blocking.
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private static void simpleGetAsyncWithExecutorPool() throws URISyntaxException, IOException, InterruptedException, ExecutionException {
        
        PATH = "https://postman-echo.com/get";

        request = HttpRequest.newBuilder()
                            .uri(new URI(PATH))      // path i.e. URL
                            .version(Version.HTTP_2) // http version
                            .headers("key1", "value1", "key2", "value2") // all headers at once
                            .header("key3", "value3") // each header at once
                            .header("key4", "value4")
                            .timeout(Duration.of(10, ChronoUnit.SECONDS)) // set request timeout
                            .GET() // without content i.e. no body
                            .build();

        // An Executor which provides threads to be used by asynchronous calls.
        // This we can limit the no. of threads used for processing requests.
        // By default, the HttpClient uses executor java.util.concurrent.Executors.newCachedThreadPool().
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        CompletableFuture<HttpResponse<String>> response1 = HttpClient.newBuilder()
                            .executor(executorService)
                            .build()
                            .sendAsync(request, HttpResponse.BodyHandlers.ofString());
                    
        CompletableFuture<HttpResponse<String>> response2 = HttpClient.newBuilder()
                            .executor(executorService)
                            .build()
                            .sendAsync(request, HttpResponse.BodyHandlers.ofString());
                    
        CompletableFuture<HttpResponse<String>> response3 = HttpClient.newBuilder()
                            .executor(executorService)
                            .build()
                            .sendAsync(request, HttpResponse.BodyHandlers.ofString());
                    
        CompletableFuture.allOf(response1, response2, response3).join();

        System.out.println("\nHTTP POST Asynchronous calls with executor thread pool");
        System.out.println("--------------------------------------------------------");                    
        System.out.println("Response1: Status Code: " + response1.get().statusCode());
        System.out.println("Response2: Status Code: " + response2.get().statusCode());
        System.out.println("Response3: Status Code: " + response3.get().statusCode());
    }

    /**
     * Simple GET request to make synchronous call with multiple requests via stream. It includes the following:
     * - version
     * - headers
     * - timeout
     * - proxy setting
     * - redirection policy
     * - cookie policy
     * - HTTP authentication
     * Note: send(): Synchronous calls block until response comes back.
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private static void simpleGetSyncWithMultiRequest() throws URISyntaxException, IOException, InterruptedException, ExecutionException {

        List<URI> targets = Arrays.asList(new URI("https://postman-echo.com/get?foo1=bar1"), new URI("https://postman-echo.com/get?foo2=bar2"));

        HttpClient client = HttpClient.newHttpClient();

        List<CompletableFuture<String>> futures = targets.stream()
                .map(target -> client.sendAsync(HttpRequest.newBuilder(target)
                                            .GET()
                                            .build(), HttpResponse.BodyHandlers.ofString())
                                    .thenApply(response -> response.body()))
                .collect(Collectors.toList());

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        System.out.println("\nHTTP GET synchronous call with multiple responses");
        System.out.println("---------------------------------------------------");                    

        if (futures.get(0).get().contains("foo1")) {
            System.out.println("bar1 contains: " + futures.get(0).get());
            System.out.println("bar2 contains: " + futures.get(1).get());
        } else {
            System.out.println("bar2 contains: " + futures.get(1).get());
            System.out.println("bar1 contains: " + futures.get(1).get());
        }        
    } 
    
    /**
     * complete exceptionally example
     */
    private static void completeExceptionally() {
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message").thenApplyAsync(String::toUpperCase,
                CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS));
        CompletableFuture<String> exceptionHandler = cf.handle((s, th) -> { return (th != null) ? "message upon cancel" : ""; });
        cf.completeExceptionally(new RuntimeException("completed exceptionally"));
        System.out.println("Was not completed exceptionally: " + cf.isCompletedExceptionally());
        try {
            cf.join();
            System.err.println("Should have thrown an exception");
        } 
        catch (CompletionException ex) { // just for testing
            System.err.println("completed exceptionally: " + ex.getCause().getMessage());
        }

        System.out.println("message upon cancel: " + exceptionHandler.join());
    }
}