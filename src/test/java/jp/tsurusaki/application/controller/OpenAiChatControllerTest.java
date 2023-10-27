package jp.tsurusaki.application.controller;

import jp.tsurusaki.domain.service.OpenAiChatService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OpenAiChatControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private OpenAiChatService openAiChatService;

  @DisplayName("/openAiChat")
  @Nested
  class OpenAiChatTest {

    private final String PATH = "/openAiChat";

    @DisplayName("200（正常系）")
    @Test
    void ok() throws Exception {

      String messageText = "リクエスト";
      String expected = "レスポンス";

      when(openAiChatService.openAiChat(messageText)).thenReturn(expected);

      String path = this.PATH.concat("?message_text=").concat(messageText);

      MvcResult result = mockMvc.perform(
              MockMvcRequestBuilders
                  .get(path))
          .andExpect(status().isOk())
          .andExpect(content().contentType("text/plain;charset=UTF-8"))
          .andReturn();

      String actual = result.getResponse().getContentAsString();
      assertThat(actual).isEqualTo(expected);
      verify(openAiChatService, times(1)).openAiChat(messageText);
    }

    @DisplayName("500（異常系）")
    @Test
    void internalServerError() throws Exception {

      String messageText = "リクエスト";

      doThrow(new RuntimeException("エラー"))
          .when(openAiChatService).openAiChat(anyString());

      String path = this.PATH.concat("?message_text=").concat(messageText);

      mockMvc.perform(
              MockMvcRequestBuilders
                  .get(path))
          .andExpect(status().isInternalServerError())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON))
          .andReturn();

      assertThatThrownBy(() -> {
        throw new RuntimeException("エラー");
      });

      verify(openAiChatService, times(1)).openAiChat(anyString());
    }
  }
}
