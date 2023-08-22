package pfr.backgamesloc.games.dal.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

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

    @ManyToMany(fetch = FetchType.LAZY,mappedBy = "languages")
    @JsonBackReference
    private List<Game> games;
}
