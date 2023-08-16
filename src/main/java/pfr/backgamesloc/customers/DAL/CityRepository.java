package pfr.backgamesloc.customers.DAL;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pfr.backgamesloc.customers.DAL.entities.City;

import java.util.Optional;

@Repository
public interface CityRepository extends CrudRepository<City, Integer> {
    Optional<City> findCityByCityNameAndPostalCode(String cityName, String postalCode);
}
