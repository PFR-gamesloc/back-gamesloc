package pfr.backgamesloc.shared.controller.dto;

import lombok.Data;
import pfr.backgamesloc.games.dal.entities.Editor;
import pfr.backgamesloc.games.dal.entities.Tag;
import pfr.backgamesloc.games.dal.entities.Type;

import java.util.List;

@Data
public class GameOrderDTO {

    private Integer gameId;

    private String gameName;

    private String gameDescr;

    private Float gamePrice;

    private String image;

    private Integer minPlayer;

    private Integer maxPlayer;

    private Integer minAge;

    private Type type;

    private Editor editor;

    private List<Tag> tags;
}
