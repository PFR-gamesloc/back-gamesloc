package pfr.backgamesloc.games.DAL.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import pfr.backgamesloc.games.DAL.entities.Game;

import java.util.List;

@Entity
@Table(name ="editor")
@Data
public class Editor {

    @Id
    @Column(name = "editor_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private short editorId;

    @Column(name = "name")
    private String name;

    @JsonBackReference
    @OneToMany(mappedBy = "editor")
    private List<Game> games;

}
