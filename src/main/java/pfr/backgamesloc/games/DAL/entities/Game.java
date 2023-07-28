package pfr.backgamesloc.games.DAL.entities;

import jakarta.persistence.*;
import lombok.Data;
import pfr.backgamesloc.customers.DAL.entities.Customer;
import pfr.backgamesloc.editors.DAL.entities.Editor;
import pfr.backgamesloc.languages.DAL.entities.Language;
import pfr.backgamesloc.opinions.DAL.entities.Opinion;
import pfr.backgamesloc.orders.DAL.entities.Order;
import pfr.backgamesloc.tags.DAL.entities.Tag;
import pfr.backgamesloc.types.DAL.entities.Type;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "game")
@Data
public class Game {

    @Id
    @Column(name = "game_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer gameId;

    @Column(name = "game_name")
    private String gameName;

    @Column(name = "game_descr")
    private String gameDescr;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "game_price")
    private Float gamePrice;

    @Column(name = "image")
    private String image;

    @Column(name = "min_player")
    private Integer minPlayer;

    @Column(name = "max_player")
    private Integer maxPlayer;

    @Column(name = "min_age")
    private Integer minAge;

    @OneToMany(mappedBy = "games")
    private List<Opinion> opinions;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private Type type;

    @ManyToOne
    @JoinColumn(name = "editor_id")
    private Editor editor;

    @ManyToMany
    @JoinTable(
            name = "tag_game",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    @ManyToMany
    @JoinTable(
            name = "language_game",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id")
    )
    private List<Language> languages;

    @ManyToMany
    @JoinTable(
            name = "order_game",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id")
    )
    private List<Order> orders;

    @ManyToMany
    @JoinTable(
            name = "customer_like",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    private List<Customer> customersLike;
}