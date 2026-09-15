package org.hit.NCKH.domain.dto.request;

import org.hit.NCKH.constant.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserCreateDto {

  @NotNull(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
  private String username;

  @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
  @Email(message = ErrorMessage.INVALID_FORMAT_EMAIL)
  private String email;

  @NotNull(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
  @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,}$", message = ErrorMessage.INVALID_FORMAT_PASSWORD)
  private String password;

  @NotBlank(message = ErrorMessage.NOT_BLANK_FIELD)
  private String confirmPassword;

}
