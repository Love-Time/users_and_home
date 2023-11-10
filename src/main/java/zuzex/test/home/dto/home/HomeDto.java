package zuzex.test.home.dto.home;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.ReadOnlyProperty;
import zuzex.test.home.dto.user.UserDto;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class HomeDto {
    private Long id;
    private String address;
    private UserDto owner;
    private Set<UserDto> resident;
}
