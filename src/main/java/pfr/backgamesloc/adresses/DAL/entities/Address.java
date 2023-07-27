package pfr.backgamesloc.adresses.DAL.entities;

import jakarta.persistence.*;
import pfr.backgamesloc.cities.DAL.entities.City;

@Entity
@Table(name="address")
public class Address {

    @Id
    @Column(name = "address_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer addressId;

    @Column(name = "street_address")
    private String streetAddress;


    @Column(name = "complementary_address")
    private String complementaryAddress;

    @OneToOne
    @JoinColumn(name = "city_id")
    private City city;

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getComplementaryAddress() {
        return complementaryAddress;
    }

    public void setComplementaryAddress(String complementaryAddress) {
        this.complementaryAddress = complementaryAddress;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}

