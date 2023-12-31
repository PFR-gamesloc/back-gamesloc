package pfr.backgamesloc.games.dal.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

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
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "type")
    private List<Game> games;
}
