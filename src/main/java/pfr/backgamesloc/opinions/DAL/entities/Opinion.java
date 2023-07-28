package pfr.backgamesloc.opinions.DAL.entities;

import jakarta.persistence.*;
import lombok.Data;
import pfr.backgamesloc.customers.DAL.entities.Customer;
import pfr.backgamesloc.games.DAL.entities.Game;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "opinion")
@Data
public class Opinion {

    @Id
    @Column(name = "opinion_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long opinionId;

    @Column(name = "comment")
    private String comment;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "publish_date")
    private Date publishDate;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game games;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customers;

}
