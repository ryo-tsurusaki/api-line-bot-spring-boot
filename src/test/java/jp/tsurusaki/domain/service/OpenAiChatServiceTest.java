package jp.tsurusaki.domain.service;

import jp.tsurusaki.domain.model.OpenAiChatData;
import jp.tsurusaki.domain.repository.OpenAIChatRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.fail;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class OpenAiChatServiceTest {

  private AutoCloseable closeable;

  @InjectMocks
  private OpenAiChatService openAiChatService;

  @Mock
  private OpenAIChatRepository openAIChatRepository;

  /**
   * executed before the test is executed.
   */
  @BeforeEach
  void setUp() {
    closeable = MockitoAnnotations.openMocks(this);
  }

  /**
   * executed after the test is executed.
   * @throws Exception internal server error.
   */
  @AfterEach
  void tearDown() throws Exception {
    closeable.close();
  }

  @DisplayName("openAiChat")
  @Nested
  class OpenAiChat {

    @DisplayName("正常系")
    @Test
    void success() {

      String messageText = "リクエスト";

      OpenAiChatData openAiChatData = OpenAiChatData.builder()
          .content("レスポンス")
          .build();
      String expected = openAiChatData.getContent();

      doReturn(openAiChatData).when(openAIChatRepository)
          .openAiChat(messageText);

      String actual = openAiChatService.openAiChat(messageText);

      assertThat(actual).isEqualTo(expected);
      verify(openAIChatRepository, times(1)).openAiChat(messageText);
    }

    @DisplayName("異常系")
    @Test
    void failure() {

      String messageText = "リクエスト";
      String errorMessage = "エラー";

      doThrow(new RuntimeException(errorMessage)).when(openAIChatRepository)
          .openAiChat(messageText);

      try {
        openAiChatService.openAiChat(messageText);
        fail("No exception Occurred.");

      } catch (Exception exception) {

        assertThat(exception.getMessage()).isEqualTo(errorMessage);
        verify(openAIChatRepository, times(1)).openAiChat(messageText);
      }
    }
  }
}
