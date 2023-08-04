package pfr.backgamesloc.customers.controllers.DTO;

import lombok.Data;
import pfr.backgamesloc.customers.DAL.entities.Address;
import pfr.backgamesloc.customers.DAL.entities.Role;
import pfr.backgamesloc.games.DAL.entities.Game;
import pfr.backgamesloc.shared.entities.Opinion;
import pfr.backgamesloc.shared.entities.Order;

import java.util.List;

@Data
public class CustomerDto {

    private Integer customerId;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private Address address;

    private List<Role> roles;

    private List<Order> orders;

    private List<Opinion> opinions;

    private List<Game> favoriteGames;
}
