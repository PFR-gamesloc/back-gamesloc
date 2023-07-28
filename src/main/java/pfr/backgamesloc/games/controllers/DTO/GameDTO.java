package pfr.backgamesloc.games.controllers.DTO;

import pfr.backgamesloc.editors.DAL.entities.Editor;

import pfr.backgamesloc.types.DAL.entities.Type;


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
