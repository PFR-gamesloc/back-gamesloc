package pfr.backgamesloc.games.controllers.dto;

import lombok.Data;
import pfr.backgamesloc.games.dal.entities.Type;

@Data
public class GameLikeDto {

    private Integer gameId;

    private String gameName;

    private Integer gamePrice;

    private String image;

    private Type type;
}

