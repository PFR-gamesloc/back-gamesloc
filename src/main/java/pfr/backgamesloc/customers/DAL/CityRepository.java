package pfr.backgamesloc.customers.DAL;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pfr.backgamesloc.customers.DAL.entities.City;

@Repository
public interface CityRepository extends CrudRepository<City, Integer> {
    City findByPostalCodeAndCityName(String postalCode, String cityName);
}
