package org.hit.NCKH.domain.dto.response;

import org.hit.NCKH.constant.CommonConstant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class TokenRefreshResponseDto {

  private String tokenType = CommonConstant.BEARER_TOKEN;

  private String accessToken;

  private String refreshToken;

  public TokenRefreshResponseDto(String accessToken, String refreshToken) {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }

}
