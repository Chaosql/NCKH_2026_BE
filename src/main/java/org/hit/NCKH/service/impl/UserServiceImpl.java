package org.hit.NCKH.service.impl;

import org.hit.NCKH.constant.ErrorMessage;
import org.hit.NCKH.constant.SortByDataConstant;
import org.hit.NCKH.domain.dto.pagination.PaginationFullRequestDto;
import org.hit.NCKH.domain.dto.pagination.PaginationResponseDto;
import org.hit.NCKH.domain.dto.response.UserDto;
import org.hit.NCKH.domain.entity.User;
import org.hit.NCKH.domain.mapper.UserMapper;
import org.hit.NCKH.exception.NotFoundException;
import org.hit.NCKH.repository.UserRepository;
import org.hit.NCKH.security.UserPrincipal;
import org.hit.NCKH.service.UserService;
import org.hit.NCKH.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  private final UserMapper userMapper;

  @Override
  public UserDto getUserById(String userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{userId}));
    return userMapper.toUserDto(user);
  }

  @Override
  public PaginationResponseDto<UserDto> getCustomers(PaginationFullRequestDto request) {
    //Pagination
    Pageable pageable = PaginationUtil.buildPageable(request, SortByDataConstant.USER);
    //Create Output
    return new PaginationResponseDto<>(null, null);
  }

  @Override
  public UserDto getCurrentUser(UserPrincipal principal) {
    User user = userRepository.getUser(principal);
    return userMapper.toUserDto(user);
  }

}
