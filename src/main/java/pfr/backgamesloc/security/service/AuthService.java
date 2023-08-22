package pfr.backgamesloc.security.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pfr.backgamesloc.customers.dal.CustomerRepository;
import pfr.backgamesloc.customers.dal.RoleRepository;
import pfr.backgamesloc.customers.dal.entities.Address;
import pfr.backgamesloc.customers.dal.entities.City;
import pfr.backgamesloc.customers.dal.entities.Customer;
import pfr.backgamesloc.customers.dal.entities.Role;
import pfr.backgamesloc.customers.services.AddressService;
import pfr.backgamesloc.customers.services.CityService;
import pfr.backgamesloc.security.controllers.dto.AuthRequest;
import pfr.backgamesloc.security.controllers.dto.RegisterRequest;
import pfr.backgamesloc.security.controllers.dto.TokenResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final TokenService tokenService;

    private final AuthenticationManager authenticationManager;

    private final CustomerRepository customerRepository;

    private final ModelMapper modelMapper;

    private final CityService cityService;

    private final AddressService addressService;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public boolean register(RegisterRequest request) {

        Optional<Customer> emailChecker = this.customerRepository.findByEmail(request.getEmail());

        Optional<Customer> phoneChecker = this.customerRepository.findByPhoneNumber(request.getPhoneNumber());

        if (emailChecker.isEmpty() && phoneChecker.isEmpty()) {

            Customer customer = modelMapper.map(request, Customer.class);
            Address address = modelMapper.map(request, Address.class);
            City city = modelMapper.map(request,City.class);

            city.setCityName(city.getCityName().replaceAll("[-']", " ").toUpperCase());


            city = this.cityService.findCityByCityNameAndPostalCode(city.getCityName(), request.getPostalCode());

            if (city == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "the city doesn't exist");
            }


            address.setCity(city);

            Address findedAddress = this.addressService.findIfAddressAddressAlreadyExist(address);

            if (findedAddress == null) {
                findedAddress = this.addressService.save(address);
            }
            address.setStreetName(address.getStreetName().toUpperCase());
            address.getCity().setCityName(address.getCity().getCityName().toUpperCase());
            customer.setFirstName(customer.getFirstName().toUpperCase());
            customer.setLastName(customer.getLastName().toUpperCase());
            customer.setAddress(findedAddress);

            List<Role> roles = new ArrayList<>();
            roles.add(this.roleRepository.findRoleByRoleName("USER"));
            customer.setRoles(roles);

            customer.setPassword(passwordEncoder.encode(customer.getPassword()));
            this.customerRepository.save(customer);

            return true;
        }
        else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exist");
        }
    }



    public TokenResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        Customer customer = customerRepository.findByEmail(request.getUsername()).orElseThrow(()-> new UsernameNotFoundException("user not found"));

        HashMap<String, Object> roles = new HashMap<>();
        StringBuilder rolesString = new StringBuilder();
        for (Role role :customer.getRoles() ){
            rolesString.append(role.getRoleName()).append(" ");
        }
        roles.put("role", rolesString.toString());
        String token = tokenService.generateToken(roles, customer);
        return new TokenResponse(token);
    }

}
