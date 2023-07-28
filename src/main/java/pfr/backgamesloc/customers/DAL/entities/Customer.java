package pfr.backgamesloc.customers.DAL.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import pfr.backgamesloc.adresses.DAL.entities.Address;
import pfr.backgamesloc.games.DAL.entities.Game;
import pfr.backgamesloc.opinions.DAL.entities.Opinion;
import pfr.backgamesloc.orders.DAL.entities.Order;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "customer")
@Data
public class Customer {

    @Id
    @Column(name = "customer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @JsonManagedReference
    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

    @OneToMany(mappedBy = "customers")
    private List<Opinion> opinions;

    @ManyToMany(mappedBy = "customersLike")
    private List<Game> games;
}