package pfr.backgamesloc.customers.DAL;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pfr.backgamesloc.customers.DAL.entities.Address;

import java.util.Optional;

@Repository
public interface AddressRepository extends CrudRepository<Address, Integer> {
    Optional<Address> findAddressByNumberAddressAndComplementaryNumberAndStreetNameAndComplementaryAddressAndCity_CityId(
            Integer numberAddress,
            String complementaryAddress,
            String streetName,
            String ComplementaryAddress,
            Integer cityId);
}
