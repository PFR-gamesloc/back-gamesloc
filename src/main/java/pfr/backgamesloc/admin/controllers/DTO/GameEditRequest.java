package pfr.backgamesloc.admin.controllers.DTO;

import lombok.Data;

import java.util.List;

@Data
public class GameEditRequest {

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
