package org.hit.NCKH.domain.mapper;

import org.hit.NCKH.domain.dto.request.UserCreateDto;
import org.hit.NCKH.domain.dto.response.UserDto;
import org.hit.NCKH.domain.entity.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

  User toUser(UserCreateDto userCreateDTO);

  @Mappings({
          @Mapping(target = "roleName", source = "role"),
  })
  UserDto toUserDto(User user);

  List<UserDto> toUserDtos(List<User> user);

}
