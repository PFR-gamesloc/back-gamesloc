package pfr.backgamesloc.customers.dal;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pfr.backgamesloc.customers.dal.entities.Address;

import java.util.Optional;

@Repository
public interface AddressRepository extends CrudRepository<Address, Integer> {
    Optional<Address> findAddressByNumberAddressAndComplementaryNumberAndStreetNameAndComplementaryAddressAndCity_CityId(
            Integer numberAddress,
            String complementaryNumber,
            String streetName,
            String complementaryAddress,
            Integer cityId);

    Address findAddressByCustomers_email(String email);
}
