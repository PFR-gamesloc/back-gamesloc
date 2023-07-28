package pfr.backgamesloc.games.DAL.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @JsonBackReference
    @OneToMany(mappedBy = "type")
    private List<Game> games;
}
