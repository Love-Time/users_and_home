package zuzex.test.home.dto.user;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.ReadOnlyProperty;

@Data
public class UserDto {
    @ReadOnlyProperty
    private Long id;
    private String name;
    @Min(value = 0, message = "age should be between 0 and 100")
    @Max(value = 100, message = "age should be between 0 and 100")
    private Short age;


}
