package org.hit.NCKH.domain.dto.request;

import org.hit.NCKH.constant.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LoginRequestDto {

  @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
  private String usernameOrEmail;

  @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
  private String password;

}
