package pfr.backgamesloc.customers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pfr.backgamesloc.customers.DAL.AddressRepository;
import pfr.backgamesloc.customers.DAL.entities.Address;
import pfr.backgamesloc.customers.controllers.DTO.AddressDto;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    /**
     * permet de retrouver une address
     * @param addressDTO
     * @return
     */
    public Address findIfAddressAddressAlreadyExist(Address addressDTO){
        return this.addressRepository
                .findAddressByNumberAddressAndComplementaryNumberAndStreetNameAndComplementaryAddressAndCity_CityId(
                addressDTO.getNumberAddress(),
                addressDTO.getComplementaryNumber(),
                addressDTO.getStreetName(),
                addressDTO.getComplementaryAddress(),
                addressDTO.getCity().getCityId()).orElse(null);
    }

    public Address save(Address address){
        return this.addressRepository.save(address);
    }

    public Address findAddressByCustomerUsername(String email) {
        return this.addressRepository.findAddressByCustomers_email(email);
    }
}
