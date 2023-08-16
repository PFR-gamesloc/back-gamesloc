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
     * Permet de retourner la city grâce a sa city
     * @param id
     * @return une city ou null
     */
    public City findCityById(Integer id){
        return this.cityRepository.findById(id).orElse(null);
    }

    public City createCity(City city) {
        return this.cityRepository.save(city);
    }

    public City getCityByPostalCodeAndName(String postalCode, String cityName) {
        return this.cityRepository.findByPostalCodeAndCityName(postalCode, cityName);
    }
}
