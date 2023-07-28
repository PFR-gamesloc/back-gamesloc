package pfr.backgamesloc.editors.DAL.entities;

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

    @OneToMany(mappedBy = "editor")
    private List<Game> games;

}
