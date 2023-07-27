package pfr.backgamesloc.games.DAL.entities;

import jakarta.persistence.*;
import pfr.backgamesloc.opinions.DAL.entities.Opinion;
import pfr.backgamesloc.types.DAL.entities.Type;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "game")
public class Game {

    @Id
    @Column(name = "game_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer gameId;
    @Column(name = "game_name")
    private String gameName;
    @Column(name = "game_descr")
    private String gameDescr;
    @Column
    private Integer stock;
    @Column(name = "game_price")
    private Float gamePrice;
    @Column
    private String image;
    @Column(name = "min_player")
    private Integer minPlayer;
    @Column(name = "max_player")
    private Integer maxPlayer;
    @Column(name = "min_age")
    private Integer minAge;
    @ManyToOne
    @JoinColumn(name = "type_id")
    private Type type;

    @ManyToOne
    @JoinColumn(name = "editor_id")
    private Editor editor;

    @OneToMany(mappedBy = "game")
    private List<TagGame> tagGameList;

    @OneToMany(mappedBy = "game")
    private List<LanguageGame> languagueGameList;

    @OneToMany(mappedBy = "game")
    private List<RentalGame> rentalGameList;

    @OneToMany(mappedBy = "game")
    private Set<Opinion> opinions;

    @OneToMany(mappedBy = "game")
    private List<CustomerLike> customerLikeList;

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameDescr() {
        return gameDescr;
    }

    public void setGameDescr(String gameDescr) {
        this.gameDescr = gameDescr;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Float getGamePrice() {
        return gamePrice;
    }

    public void setGamePrice(Float gamePrice) {
        this.gamePrice = gamePrice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getMinPlayer() {
        return minPlayer;
    }

    public void setMinPlayer(Integer minPlayer) {
        this.minPlayer = minPlayer;
    }

    public Integer getMaxPlayer() {
        return maxPlayer;
    }

    public void setMaxPlayer(Integer maxPlayer) {
        this.maxPlayer = maxPlayer;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Editor getEditor() {
        return editor;
    }

    public void setEditor(Editor editor) {
        this.editor = editor;
    }

    public List<TagGame> getTagGameList() {
        return tagGameList;
    }

    public void setTagGameList(List<TagGame> tagGameList) {
        this.tagGameList = tagGameList;
    }

    public List<LanguageGame> getLanguagueGameList() {
        return languagueGameList;
    }

    public void setLanguagueGameList(List<LanguageGame> languagueGameList) {
        this.languagueGameList = languagueGameList;
    }

    public List<RentalGame> getRentalGameList() {
        return rentalGameList;
    }

    public void setRentalGameList(List<RentalGame> rentalGameList) {
        this.rentalGameList = rentalGameList;
    }

    public List<CustomerLike> getCustomerLikeList() {
        return customerLikeList;
    }

    public void setCustomerLikeList(List<CustomerLike> customerLikeList) {
        this.customerLikeList = customerLikeList;
    }

    public Set<Opinion> getOpinions() {
        return opinions;
    }

    public void setOpinions(Set<Opinion> opinions) {
        this.opinions = opinions;
    }
}
