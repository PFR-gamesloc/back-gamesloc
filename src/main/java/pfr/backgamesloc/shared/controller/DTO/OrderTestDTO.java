package pfr.backgamesloc.shared.controller.DTO;

import lombok.Data;

import java.util.List;

@Data
public class OrderTestDTO {
    private Float price;

    private List<Integer> gamesId;
}
