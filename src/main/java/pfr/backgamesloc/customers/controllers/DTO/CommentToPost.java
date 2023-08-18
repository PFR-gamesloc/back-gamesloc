package pfr.backgamesloc.customers.controllers.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentToPost {
    @NotNull
    private Integer gameId;

    private String comment;

    private Integer rating;
}
