package pfr.backgamesloc.shared.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequest {

    @NotNull
    private Float price;

    @NotNull
    @NotEmpty
    private List<Integer> gamesId;
}
