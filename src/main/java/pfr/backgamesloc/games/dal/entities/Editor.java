package pfr.backgamesloc.games.dal.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "editor")
@Data
public class Editor {

    @Id
    @Column(name = "editor_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer editorId;

    @Column(name = "name")
    private String name;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "editor")
    private List<Game> games;

}
