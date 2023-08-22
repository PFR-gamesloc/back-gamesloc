package pfr.backgamesloc.shared.entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Data;
import pfr.backgamesloc.customers.dal.entities.Customer;
import pfr.backgamesloc.games.dal.entities.Game;

import java.util.Date;
import java.util.List;

@Entity
@Table(name ="orders")
@Data
public class Order {

    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    @Column(name = "order_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    @Column(name = "return_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date returnDate;

    @Column(name = "price")
    private Float price;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToMany( fetch = FetchType.LAZY ,mappedBy = "orders")
    @JsonManagedReference
    private List<Game> games;

}
