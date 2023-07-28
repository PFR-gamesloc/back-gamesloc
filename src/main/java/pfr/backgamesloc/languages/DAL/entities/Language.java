package pfr.backgamesloc.languages.DAL.entities;

import jakarta.persistence.*;
import lombok.Data;
import pfr.backgamesloc.games.DAL.entities.Game;

import java.util.List;


@Entity
@Table(name = "language")
@Data
public class Language {
    @Id
    @Column(name = "language_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer languageId;

    @Column(name = "acronym")
    private String acronym;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "languages")
    private List<Game> games;
}
