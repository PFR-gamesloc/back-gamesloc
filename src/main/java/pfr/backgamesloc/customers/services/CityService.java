package pfr.backgamesloc.customers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pfr.backgamesloc.customers.DAL.CityRepository;
import pfr.backgamesloc.customers.DAL.entities.City;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    /**
     *
     * @param cityName
     * @param postalCode
     * @return a city or throw an Exception
     */
    public City findCityByCityNameAndPostalCode(String cityName, String postalCode){
        return this.cityRepository.findCityByCityNameAndPostalCode(cityName, postalCode).orElse(null);
    }

    public City createCity(City city) {
        return this.cityRepository.save(city);
    }

    public City getCityByPostalCodeAndName(String postalCode, String cityName) {
        return this.cityRepository.findByPostalCodeAndCityName(postalCode, cityName);
    }
}
