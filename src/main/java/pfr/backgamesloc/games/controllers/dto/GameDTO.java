package pfr.backgamesloc.games.controllers.dto;

import lombok.Data;
import pfr.backgamesloc.games.dal.entities.Editor;
import pfr.backgamesloc.games.dal.entities.Language;
import pfr.backgamesloc.games.dal.entities.Tag;
import pfr.backgamesloc.games.dal.entities.Type;
import pfr.backgamesloc.shared.entities.Opinion;

import java.util.List;

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

    private List<Language> languages;

    private List<Tag> tags;

    private List<Opinion> opinions;

}

