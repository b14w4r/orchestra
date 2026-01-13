import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Main {
  public static void main(String[] args) throws Exception {
    int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8080"));
    HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

    server.createContext("/api/sentiment", exchange -> {
      String query = exchange.getRequestURI().getQuery();
      String text = "";
      if (query != null) {
        for (String p : query.split("&")) {
          String[] kv = p.split("=", 2);
          if (kv.length == 2 && kv[0].equals("text")) text = URLDecoder.decode(kv[1], "UTF-8");
        }
      }

      String t = text.toLowerCase();
      String sentiment = "neutral";
      if (t.contains("good") || t.contains("like") || t.contains("ok") || t.contains("круто") || t.contains("хорош")) sentiment = "positive";
      if (t.contains("bad") || t.contains("hate") || t.contains("ужас") || t.contains("плох")) sentiment = "negative";

      String body = "{\"sentiment\":\"" + sentiment + "\",\"text\":\"" + text.replace("\"","'") + "\"}";
      byte[] bytes = body.getBytes(StandardCharsets.UTF_8);

      exchange.getResponseHeaders().add("Content-Type", "application/json; charset=utf-8");
      exchange.sendResponseHeaders(200, bytes.length);
      try (OutputStream os = exchange.getResponseBody()) { os.write(bytes); }
    });

    server.start();
    System.out.println("Started on port " + port);
  }
}

