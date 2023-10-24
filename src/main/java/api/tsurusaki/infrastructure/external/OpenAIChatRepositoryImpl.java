package api.tsurusaki.infrastructure.external;

import api.tsurusaki.domain.repository.OpenAIChatRepository;
import api.tsurusaki.domain.value.OpenAiChatConfig;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
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
    public String openAiChat(String messageText) {

        String content =  "";
        String message = "{\"role\": \"system\", \"content\": \"返答は日本語で\"},{\"role\": \"user\", \"content\": \" "+ messageText +" \"}";

        try {
            // HTTPリクエストの作成
            URL obj = new URL(this.openAiChatConfig.getUrl());
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Authorization", "Bearer " + this.openAiChatConfig.getApiKey());
            con.setDoOutput(true);

            // リクエストの送信
            OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
            out.write("{\"model\": \"" + this.openAiChatConfig.getModel() + "\", \"messages\": [" + message + "]}");
            out.close();

            // レスポンスの取得
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            Gson gson = new Gson();
            JsonObject jsonResponse = gson.fromJson(in, JsonObject.class);
            JsonArray choicesArray = jsonResponse.getAsJsonArray("choices");
            JsonObject messageObject = choicesArray.get(0).getAsJsonObject().get("message").getAsJsonObject();
            content = messageObject.get("content").getAsString();

            // レスポンスの出力
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return content;
    }
}
