package zuzex.test.home.dto.authentication;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRegisterRequestDto {


    @NotEmpty(message = "name should not be empty")
    private String name;

    @NotEmpty(message = "email should not be empty")
    @Email(message = "email should be valid")
    private String email;


    @Min(value = 0, message = "age should be between 0 and 100")
    @Max(value = 100, message = "age should be between 0 and 100")
    private Short age;

    @NotEmpty(message = "password should not be empty")
    @Size(min = 8, max = 30, message = "password length should be between 8 and 30")
    private String password;

}
