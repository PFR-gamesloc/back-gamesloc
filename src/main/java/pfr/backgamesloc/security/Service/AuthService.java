package pfr.backgamesloc.security.Service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.MultiMap;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pfr.backgamesloc.customers.DAL.CustomerRepository;
import pfr.backgamesloc.customers.DAL.RoleRepository;
import pfr.backgamesloc.customers.DAL.entities.Address;
import pfr.backgamesloc.customers.DAL.entities.City;
import pfr.backgamesloc.customers.DAL.entities.Customer;
import pfr.backgamesloc.customers.DAL.entities.Role;
import pfr.backgamesloc.customers.controllers.DTO.CustomerDto;
import pfr.backgamesloc.customers.services.AddressService;
import pfr.backgamesloc.customers.services.CityService;
import pfr.backgamesloc.customers.services.CustomerService;
import pfr.backgamesloc.security.controllers.DTO.AuthRequest;
import pfr.backgamesloc.security.controllers.DTO.RegisterRequest;
import pfr.backgamesloc.security.controllers.DTO.Token;

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

    public Token register(RegisterRequest request) {

        Optional<Customer> checker = this.customerRepository.findByEmail(request.getEmail());

        if (checker.isEmpty()) {
            Customer customer = modelMapper.map(request, Customer.class);
            City city = this.cityService.findCityById(request.getCityId());

            if (city == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "the city doesn't exist");
            }

            Address address = modelMapper.map(request, Address.class);

            address.setCity(city);

            if (this.addressService.findIfAddressAddressAlreadyExist(address) == null) {
                this.addressService.save(address);
            }
            customer.setAddress(address);

            List<Role> roles = new ArrayList<>();
            roles.add(this.roleRepository.findRoleByRoleName("USER"));
            customer.setRoles(roles);

            this.customerRepository.save(customer);

            HashMap<String, Object> rolesHM = new HashMap<>();

            StringBuilder rolesString = new StringBuilder();

            for (Role role :customer.getRoles() ){
                rolesString.append(role.getRoleName()).append(" ");
            }

            rolesHM.put("Role", rolesString.toString());

            String token = tokenService.generateToken(rolesHM, customer);
            return new Token(token);
        }
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "the user already exist with the email: " + request.getEmail() );
        }
    }



    public Token authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        Customer customer = customerRepository.findByEmail(request.getUsername()).orElseThrow();

        HashMap<String, Object> roles = new HashMap<>();
        StringBuilder rolesString = new StringBuilder();
        for (Role role :customer.getRoles() ){
            rolesString.append(role.getRoleName()).append(" ");
        }
        roles.put("Role", rolesString.toString());
        String token = tokenService.generateToken(roles, customer);
        return new Token(token);
    }

}
