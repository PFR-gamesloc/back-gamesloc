package pfr.backgamesloc.types.DAL.entities;

import jakarta.persistence.*;
import lombok.Data;
import pfr.backgamesloc.games.DAL.entities.Game;

import java.util.List;

@Entity
@Table(name = "type")
@Data
public class Type {

    @Id
    @Column(name = "type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer typeId;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "type")
    private List<Game> games;
}
