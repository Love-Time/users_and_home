package zuzex.test.home.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import zuzex.test.home.dto.home.HomeDto;
import zuzex.test.home.dto.user.UserDto;
import zuzex.test.home.dto.user.UserResponseDto;
import zuzex.test.home.entity.Home;
import zuzex.test.home.entity.User;

import java.util.List;

@Mapper
public interface HomeMapper {
    HomeMapper INSTANCE = Mappers.getMapper(HomeMapper.class);

    HomeDto toDto(Home home);
    List<HomeDto> toDto(List<Home> home);




}
