package pfr.backgamesloc.games.controllers.DTO;

import lombok.Data;
import pfr.backgamesloc.games.DAL.entities.Type;

@Data
public class GameLikeDto {

    private Integer gameId;

    private String gameName;

    private Integer gamePrice;

    private String image;

    private Type type;
}

