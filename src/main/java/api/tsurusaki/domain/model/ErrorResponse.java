package api.tsurusaki.domain.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorResponse {
    private String timestamp;
    private int code;
    private String uuId;
    private String error;
}
