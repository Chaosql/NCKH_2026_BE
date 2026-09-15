package org.hit.NCKH.domain.dto.response;

import org.hit.NCKH.domain.dto.common.DateAuditingDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto extends DateAuditingDto {

  private String id;

  private String username;

  private String email;

  private String roleName;

}

