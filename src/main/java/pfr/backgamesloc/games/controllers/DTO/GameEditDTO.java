package pfr.backgamesloc.games.controllers.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import pfr.backgamesloc.games.DAL.entities.Editor;
import pfr.backgamesloc.games.DAL.entities.Language;
import pfr.backgamesloc.games.DAL.entities.Tag;
import pfr.backgamesloc.games.DAL.entities.Type;

import java.util.List;

@Data
public class GameEditDTO {

    private Integer gameId;

    private String gameName;

    private String gameDescr;

    private Integer stock;

    private Float gamePrice;

    private String image;

    private Integer minPlayer;

    private Integer maxPlayer;

    private Integer minAge;

    private Integer typeId;

    private Integer editorId;

    private List<Integer> languagesId;

    private List<Integer> tagsId;
}
