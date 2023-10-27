package jp.tsurusaki.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "config.open-ai")
@Data
public class OpenAiChatConfig {

  private String apiKey;
  private String model;
  private String url;
}
