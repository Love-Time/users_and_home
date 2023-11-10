package zuzex.test.home.dto.home;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.ReadOnlyProperty;
import zuzex.test.home.dto.user.UserDto;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HomeRequestDto {
    @NotNull(message = "address should not be null")
    private String address;

}
