package pfr.backgamesloc.games.DAL.entities;

import jakarta.persistence.*;
import lombok.Data;
import pfr.backgamesloc.games.DAL.entities.Game;

import java.util.List;

@Entity
@Table(name = "tag")
@Data
public class Tag {

    @Id
    @Column(name = "tag_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private short tagId;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<Game> games;
}
