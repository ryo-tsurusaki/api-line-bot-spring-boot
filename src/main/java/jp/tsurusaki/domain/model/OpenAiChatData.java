package jp.tsurusaki.domain.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OpenAiChatData {

  private String content;
}
