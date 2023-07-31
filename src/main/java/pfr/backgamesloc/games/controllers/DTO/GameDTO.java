package pfr.backgamesloc.games.controllers.DTO;


import lombok.Data;
import pfr.backgamesloc.games.DAL.entities.Editor;
import pfr.backgamesloc.games.DAL.entities.Type;

@Data
public class GameDTO {

    private Integer gameId;

    private String gameName;

    private String gameDescr;

    private Integer stock;

    private Float gamePrice;

    private String image;

    private Integer minPlayer;

    private Integer maxPlayer;

    private Integer minAge;

    private Type type;

    private Editor editor;


}

