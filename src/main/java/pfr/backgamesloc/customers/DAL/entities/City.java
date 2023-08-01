package pfr.backgamesloc.customers.DAL.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "city")
@Data
public class City {

    @Id
    @Column(name = "city_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cityId;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "city_name")
    private String cityName;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "city")
    private List<Address> addresses;
}
