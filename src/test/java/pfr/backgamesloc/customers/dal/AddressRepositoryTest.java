package pfr.backgamesloc.customers.dal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import pfr.backgamesloc.customers.dal.entities.Address;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AddressRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;

    @Test
    @Sql("AddressFindByNumberAddressAndComplementaryNumberAndStreetNameAndComplementaryAddressAndCity_CityIdTest.sql")
    public void findAddressByNumberAddressAndComplementaryNumberAndStreetNameAndComplementaryAddressAndCity_CityId() {
        Optional<Address> address = addressRepository.findAddressByNumberAddressAndComplementaryNumberAndStreetNameAndComplementaryAddressAndCity_CityId(
                123,
                "A",
                "Rue de la Paix",
                "Appartement 456",
                1
        );

        assertThat(address).isPresent();
        Address addressCheck = address.get();
        assertThat(addressCheck.getStreetName()).isEqualTo("Rue de la Paix");
    }
    @Test
    @Sql("AddressFindByEmail.sql")
    public void findByCustomerEmail() {
        Address address = addressRepository.findAddressByCustomers_email("john.doe@example.com");
        assertThat(address.getStreetName()).isEqualTo("Rue de la Paix");
    }

}



