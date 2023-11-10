package zuzex.test.home.dto.home;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResidentAddDto {
    @NotNull(message = "homeId should not be null")
    private Long homeId;
    @NotNull(message = "userId should not be null")
    private Long userId;
}
