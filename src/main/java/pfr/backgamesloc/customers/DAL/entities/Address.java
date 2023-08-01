package pfr.backgamesloc.customers.DAL.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import pfr.backgamesloc.customers.DAL.entities.City;
import pfr.backgamesloc.customers.DAL.entities.Customer;

import java.util.List;

@Entity
@Table(name="address")
@Data
public class Address {

    @Id
    @Column(name = "address_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer addressId;

    @Column(name = "street_name")
    private String streetName;

    @Column(name = "number_address")
    private Integer numberAddress;

    @Column(name = "complementary_number")
    private String complementaryNumber;

    @Column(name = "complementary_address")
    private String complementaryAddress;

    @JsonManagedReference
    @ManyToOne()
    @JoinColumn(name = "city_id")
    private City city;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "address")
    private List<Customer> customers;

}

