package pfr.backgamesloc.customers.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pfr.backgamesloc.customers.DAL.CityRepository;
import pfr.backgamesloc.customers.DAL.entities.City;

import java.util.List;

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
        return this.cityRepository.findCityByCityNameAndPostalCode(cityName, postalCode).orElseThrow(() -> new EntityNotFoundException("City not found"));
    }

    public City createCity(City city) {
        return this.cityRepository.save(city);
    }

    public List<City> getAllLikeParam(String param){
        return this.cityRepository.findCityByCityNameLikeOrPostalCodeLike(param, param);
    }
}
