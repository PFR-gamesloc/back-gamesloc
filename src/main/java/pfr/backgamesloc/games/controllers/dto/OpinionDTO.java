package pfr.backgamesloc.games.controllers.dto;

import lombok.Data;
import pfr.backgamesloc.customers.controllers.dto.CustomerOpinionDto;

import java.util.Date;

@Data
public class OpinionDTO {
    private String comment;
    private Integer rating;
    private Date publishDate;
    private CustomerOpinionDto customer;
}
