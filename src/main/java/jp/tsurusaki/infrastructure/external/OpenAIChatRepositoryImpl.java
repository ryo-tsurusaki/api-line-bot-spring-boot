package jp.tsurusaki.infrastructure.external;

import jp.tsurusaki.domain.model.OpenAiChatData;
import jp.tsurusaki.domain.repository.OpenAIChatRepository;
import jp.tsurusaki.infrastructure.config.OpenAiChatConfig;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OpenAIChatRepositoryImpl implements OpenAIChatRepository {

  @Autowired
  private OpenAiChatConfig openAiChatConfig;

  /**
   * {@inheritDoc}
   */
  @Override
  public OpenAiChatData openAiChat(final String messageText) {

    String message = "{\"role\": \"system\", \"content\": \"返答は日本語で\"},"
        + "{\"role\": \"user\", \"content\": \" " + messageText + " \"}";

    try {
      BufferedReader in = this.sendRequest(message);

      Gson gson = new Gson();
      JsonObject jsonResponse = gson.fromJson(in, JsonObject.class);
      JsonArray choicesArray =
          jsonResponse.getAsJsonArray("choices");
      JsonObject messageObject = choicesArray.get(0).getAsJsonObject()
          .get("message").getAsJsonObject();
      String content = messageObject.get("content").getAsString();

      return OpenAiChatData.builder()
          .content(content)
          .build();

    } catch (Exception exception) {
      throw new RuntimeException(exception.getMessage());
    }
  }

  /**
   * send OpenAI request.
   *
   * @param message message string.
   * @return response stream.
   */
  private BufferedReader sendRequest(String message) {

    HttpURLConnection httpURLConnection = this.getHttpURLConnection();

    try (OutputStreamWriter out = new OutputStreamWriter(
        httpURLConnection.getOutputStream())) {

      out.write("{\"model\": \"" + this.openAiChatConfig.getModel()
          + "\", \"messages\": [" + message + "]}");
      return new BufferedReader(
          new InputStreamReader(httpURLConnection.getInputStream()));

    } catch (Exception exception) {
      throw new RuntimeException(exception);
    }
  }

  /**
   * get httpUrlConnection
   *
   * @return HttpURLConnection object.
   */
  private HttpURLConnection getHttpURLConnection() {

    try {
      URL url = new URL(this.openAiChatConfig.getUrl());
      HttpURLConnection httpURLConnection =
          (HttpURLConnection) url.openConnection();
      httpURLConnection.setRequestMethod("POST");
      httpURLConnection.setRequestProperty("Content-Type", "application/json");
      httpURLConnection.setRequestProperty("Authorization",
          "Bearer " + this.openAiChatConfig.getApiKey());
      httpURLConnection.setDoOutput(true);
      return httpURLConnection;
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
  }
}
