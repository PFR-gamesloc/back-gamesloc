package pfr.backgamesloc.customers.dal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import pfr.backgamesloc.customers.dal.entities.City;

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
        assertThat(city.get().getCityName()).isEqualTo("Masevaux");
    }

}
