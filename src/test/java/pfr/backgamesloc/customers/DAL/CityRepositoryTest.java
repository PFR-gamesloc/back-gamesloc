package pfr.backgamesloc.customers.DAL;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import pfr.backgamesloc.customers.DAL.entities.City;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CityRepositoryTest {

    @Autowired
    private CityRepository cityRepository;

    @Test
    @Sql("findCityByCityNameAndPostalCode.sql")
    public void findCityByCityNameAndPostalCode(){
        Optional<City> city = cityRepository.findCityByCityNameAndPostalCode(
                "Masevaux",
                "68290"
        );
        assertThat(city).isPresent();
        assertThat(city.get().getCityName()).isEqualTo("Masevaux");
    }

    @Test
    @Sql("findByPostalCodeLikeOrCityNameLike.sql")
    public void findCityByCityNameLikeOrPostalCodeLike(){
        List<City> cities = cityRepository.findCityByCityNameLikeOrPostalCodeLike("%12%","%Ci%");
        assertThat(cities).hasSize(2);
    }
}
