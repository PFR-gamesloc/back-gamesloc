package pfr.backgamesloc.games.controllers.DTO;

import lombok.Data;

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

    private List<Integer> languages;

    private List<Integer> tags;
}