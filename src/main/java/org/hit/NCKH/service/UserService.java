package org.hit.NCKH.service;

import org.hit.NCKH.domain.dto.pagination.PaginationFullRequestDto;
import org.hit.NCKH.domain.dto.pagination.PaginationResponseDto;
import org.hit.NCKH.domain.dto.response.UserDto;
import org.hit.NCKH.security.UserPrincipal;

public interface UserService {

  UserDto getUserById(String userId);

  PaginationResponseDto<UserDto> getCustomers(PaginationFullRequestDto request);

  UserDto getCurrentUser(UserPrincipal principal);

}
