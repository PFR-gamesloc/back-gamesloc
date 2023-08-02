package pfr.backgamesloc.shared.controller.DTO;

import lombok.Data;
import pfr.backgamesloc.games.DAL.entities.Editor;
import pfr.backgamesloc.games.DAL.entities.Language;
import pfr.backgamesloc.games.DAL.entities.Tag;
import pfr.backgamesloc.games.DAL.entities.Type;

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
