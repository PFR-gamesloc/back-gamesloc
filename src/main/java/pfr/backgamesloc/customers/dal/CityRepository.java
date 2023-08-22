package pfr.backgamesloc.customers.dal;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pfr.backgamesloc.customers.dal.entities.City;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends CrudRepository<City, Integer> {
    Optional<City> findCityByCityNameAndPostalCode(String cityName, String postalCode);

    List<City> findCityByCityNameLikeOrPostalCodeLike(String postalCode, String cityName);
}
