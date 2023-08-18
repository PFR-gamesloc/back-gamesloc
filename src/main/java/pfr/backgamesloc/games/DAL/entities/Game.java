package pfr.backgamesloc.games.DAL.entities;


import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;
import pfr.backgamesloc.customers.DAL.entities.Customer;
import pfr.backgamesloc.shared.entities.Opinion;
import pfr.backgamesloc.shared.entities.Order;

import java.util.List;

@Entity
@Table(name = "game")
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToMany(fetch = FetchType.LAZY ,mappedBy = "game")
    @JsonManagedReference
    private List<Opinion> opinions;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "type_id")
    private Type type;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "editor_id")
    private Editor editor;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tag_game",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;


    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable(
            name = "language_game",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id")
    )
    private List<Language> languages ;

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinTable(
            name = "order_game",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id")
    )
    private List<Order> orders;

    @ManyToMany
    @JsonBackReference
    @JoinTable(
            name = "customer_like",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    private List<Customer> customersLike;
}