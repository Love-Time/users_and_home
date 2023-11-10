package zuzex.test.home.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import zuzex.test.home.dto.user.UserDto;
import zuzex.test.home.dto.user.UserResponseDto;
import zuzex.test.home.entity.User;

import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toDto(User user);
    List<UserDto> toDto(List<User> users);

    User fromDto(UserDto dto);
    User fromDto(UserResponseDto dto);


}
