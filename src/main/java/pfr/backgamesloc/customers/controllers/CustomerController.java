package pfr.backgamesloc.customers.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pfr.backgamesloc.customers.DAL.entities.Address;
import pfr.backgamesloc.customers.DAL.entities.City;
import pfr.backgamesloc.customers.DAL.entities.Customer;
import pfr.backgamesloc.customers.controllers.DTO.*;
import pfr.backgamesloc.customers.services.AddressService;
import pfr.backgamesloc.customers.services.CustomerService;
import pfr.backgamesloc.games.DAL.entities.Game;
import pfr.backgamesloc.games.controllers.DTO.GameLikeDto;
import pfr.backgamesloc.games.services.GameService;
import pfr.backgamesloc.security.Service.TokenService;
import pfr.backgamesloc.shared.controller.DTO.OrderDTO;
import pfr.backgamesloc.shared.entities.Order;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final TokenService tokenService;

    private final CustomerService customerService;

    private final ModelMapper modelMapper;

    private final AddressService addressService;

    private final GameService gameService;

    @GetMapping("/me")
    public CustomerDto getCurrentCustomer(HttpServletRequest request) {
        Customer customer = this.getCurrentUser(request);
        return transformCustomerToCustomerDto(customer);
    }

    @GetMapping("/me/orders")
    public List<OrderDTO> getOrdersOfCurrentUser(HttpServletRequest request) {
        Customer customer = this.getCurrentUser(request);
        List<Order> orders = this.customerService.getOrdersByCustomerId(customer.getCustomerId());
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (Order order : orders) {
            orderDTOList.add(this.transformOrderToOrderDto(order));
        }
        return orderDTOList;
    }

    @GetMapping("/me/address")
    public AddressDto getAddressOfCurrentUser(HttpServletRequest request) {
        Customer customer = this.getCurrentUser(request);
        Address address = this.addressService.findAddressByCustomerUsername(customer.getEmail());
        return modelMapper.map(address, AddressDto.class);
    }

    @GetMapping("/me/favs")
    public List<GameLikeDto> getLikesOfCurrentUser(HttpServletRequest request) {
        Customer customer = this.getCurrentUser(request);
        List<Game> games = this.gameService.findFavsByCustomerId(customer.getCustomerId());
        List<GameLikeDto> gameDTOList = new ArrayList<>();
        for (Game game : games) {
            gameDTOList.add(this.transformGameTOGameDTO(game));
        }
        return gameDTOList;
    }


    @PostMapping("/me/favs/add")
    //@PreAuthorize("hasRole('USER')")
    public Customer addToFavorites(HttpServletRequest request, @RequestBody AddGameToCustomerFavDTO gameToAdd) {
        System.out.println(gameToAdd);
        Customer customer = this.getCurrentUser(request);
        this.gameService.addGameToFavorites(customer.getCustomerId(), gameToAdd.getId());
        return customer;
    }


    private OrderDTO transformOrderToOrderDto(Order order) {
        return modelMapper.map(order, OrderDTO.class);
    }

    private CustomerDto transformCustomerToCustomerDto(Customer customer) {
        return modelMapper.map(customer, CustomerDto.class);
    }

    private Customer getCurrentUser(HttpServletRequest request) {
        String jwt = tokenService.getJwt(request.getHeader("Authorization"));
        String userEmail = tokenService.extractUsername(jwt);
        return this.customerService.getCustomerByUsername(userEmail);
    }

    public GameLikeDto transformGameTOGameDTO(Game game) {
        return this.modelMapper.map(game, GameLikeDto.class);
    }

    @PutMapping("/edit")
    public ResponseEntity<Customer> editCustomer(HttpServletRequest request, @RequestBody CustomerEditDTO customerEdit) {
        Customer customer = this.getCurrentUser(request);
        customer.setFirstName(customerEdit.getFirstName());
        customer.setLastName(customerEdit.getLastName());
        customer.setEmail(customerEdit.getEmail());
        customer.setPhoneNumber(customerEdit.getPhoneNumber());

        this.customerService.editCustomerById(customer.getCustomerId(), customer);

        return ResponseEntity.ok(customer);
    }

    @PutMapping("/edit/address")
    public ResponseEntity<Customer> editCustomerAddress(HttpServletRequest request, @RequestBody CustomerAddressEditDTO customerAddressEditDTO) {
        Customer customer = this.getCurrentUser(request);

        Address address = customer.getAddress();

        address.setNumberAddress(customerAddressEditDTO.getNumberAddress());
        address.setComplementaryNumber(customerAddressEditDTO.getComplementaryNumber());
        address.setStreetName(customerAddressEditDTO.getStreetName());
        address.setComplementaryAddress(customerAddressEditDTO.getComplementaryAddress());

        CityDto cityDto = new CityDto();

        cityDto.setPostalCode(customerAddressEditDTO.getPostalCode());
        cityDto.setCityName(customerAddressEditDTO.getCityName());

        address.setCity(this.transformCityDTOtoCity(cityDto));

        customer.setAddress(address);

        this.customerService.editCustomerById(customer.getCustomerId(), customer);

        return ResponseEntity.ok(customer);
    }

    public City transformCityDTOtoCity(CityDto cityDto) {
        return this.modelMapper.map(cityDto, City.class);
    }
}
