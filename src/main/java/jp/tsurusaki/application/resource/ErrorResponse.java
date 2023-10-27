package jp.tsurusaki.application.resource;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorResponse {

  /**
   * constructor of ErrorResponse.
   *
   * @param timestamp timestamp string.
   * @param code      code
   * @param uuId      UUID
   * @param message   message string.
   */
  public ErrorResponse(String timestamp, int code, String uuId,
                       String message) {

    this.error = ErrorData.builder()
        .timestamp(timestamp)
        .code(code)
        .uuId(uuId)
        .message(message)
        .build();
  }

  private final ErrorData error;

  @Builder
  @Getter
  private static class ErrorData {

    private String timestamp;
    private int code;
    private String uuId;
    private String message;
  }
}
