package pfr.backgamesloc.games.controllers.DTO;

import lombok.Data;
import pfr.backgamesloc.customers.controllers.DTO.CustomerOpinionDto;

import java.util.Date;

@Data
public class OpinionDTO {
    private String comment;
    private Integer rating;
    private Date publishDate;
    private CustomerOpinionDto customer;
}
