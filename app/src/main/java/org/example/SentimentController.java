package org.example;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SentimentController {

  @GetMapping("/api/sentiment")
  public Map<String, String> sentiment(@RequestParam(defaultValue = "") String text) {
    String t = text.toLowerCase();
    String sentiment = "neutral";

    if (t.contains("good") || t.contains("like") || t.contains("ok")
        || t.contains("круто") || t.contains("хорош"))
      sentiment = "positive";
    if (t.contains("bad") || t.contains("hate")
        || t.contains("ужас") || t.contains("плох"))
      sentiment = "negative";

    return Map.of("sentiment", sentiment, "text", text);
  }
}
