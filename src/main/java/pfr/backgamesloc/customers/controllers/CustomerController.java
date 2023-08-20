package pfr.backgamesloc.customers.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pfr.backgamesloc.customers.DAL.entities.Address;
import pfr.backgamesloc.customers.DAL.entities.City;
import pfr.backgamesloc.customers.DAL.entities.Customer;
import pfr.backgamesloc.customers.controllers.DTO.*;
import pfr.backgamesloc.customers.services.AddressService;
import pfr.backgamesloc.customers.services.CityService;
import pfr.backgamesloc.customers.services.CustomerService;
import pfr.backgamesloc.games.DAL.entities.Game;
import pfr.backgamesloc.games.controllers.DTO.GameLikeDto;
import pfr.backgamesloc.games.services.GameService;
import pfr.backgamesloc.security.Service.TokenService;
import pfr.backgamesloc.shared.controller.DTO.OrderDTO;
import pfr.backgamesloc.shared.controller.DTO.CreateOrderRequest;
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

    private final CityService cityService;

    @GetMapping("/me")
    public ResponseEntity<CustomerDto> getCurrentCustomer(HttpServletRequest request) {
        Customer customer = this.getCurrentUser(request);
        return new ResponseEntity<>(modelMapper.map(customer, CustomerDto.class),HttpStatus.OK);
    }

    @GetMapping("/me/orders")
    public ResponseEntity<List<OrderDTO>> getOrdersOfCurrentUser(HttpServletRequest request) {
        Customer customer = this.getCurrentUser(request);
        List<Order> orders = this.customerService.getOrdersByCustomerId(customer.getCustomerId());
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (Order order : orders) {
            orderDTOList.add(modelMapper.map(order, OrderDTO.class));
        }
        return new ResponseEntity<>(orderDTOList, HttpStatus.OK);
    }

    @GetMapping("/me/address")
    public ResponseEntity<AddressDto> getAddressOfCurrentUser(HttpServletRequest request) {
        Customer customer = this.getCurrentUser(request);
        Address address = this.addressService.findAddressByCustomerUsername(customer.getEmail());
        return  new ResponseEntity<>(modelMapper.map(address, AddressDto.class), HttpStatus.OK);
    }

    @GetMapping("/me/favs")
    public ResponseEntity<List<GameLikeDto>> getLikesOfCurrentUser(HttpServletRequest request) {
        Customer customer = this.getCurrentUser(request);
        List<Game> games = this.gameService.findFavsByCustomerId(customer.getCustomerId());
        List<GameLikeDto> gameDTOList = new ArrayList<>();
        for (Game game : games) {
            gameDTOList.add(this.modelMapper.map(game,GameLikeDto.class));
        }
        return new ResponseEntity<>(gameDTOList, HttpStatus.OK);
    }


    @PostMapping("/me/favs/add")
    public ResponseEntity<CustomerDto> addToFavorites(HttpServletRequest request, @RequestBody AddGameToCustomerFavDTO gameToAdd) {
        System.out.println(gameToAdd);
        Customer customer = this.getCurrentUser(request);
        this.gameService.addGameToFavorites(customer.getCustomerId(), gameToAdd.getId());
        return  new ResponseEntity<>(this.modelMapper.map(customer,CustomerDto.class),HttpStatus.OK);
    }

    @PostMapping("/me/favs/remove")
    public ResponseEntity<CustomerDto> removeToFavorites(HttpServletRequest request, @RequestBody AddGameToCustomerFavDTO gameToRemove) {
        System.out.println(gameToRemove);
        Customer customer = this.getCurrentUser(request);
        this.gameService.removeGameToFavorites(customer.getCustomerId(), gameToRemove.getId());
        return  new ResponseEntity<>(this.modelMapper.map(customer,CustomerDto.class),HttpStatus.OK);
    }

    @PostMapping("/me/create-order")
    public ResponseEntity<CustomerDto> addOrder(HttpServletRequest request, @RequestBody @Valid CreateOrderRequest orderDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Informations manquantes");
        }
        Customer customer = this.getCurrentUser(request);
        customerService.createOrderForCustomer(customer, orderDTO);
        return  new ResponseEntity<>(this.modelMapper.map(customer,CustomerDto.class),HttpStatus.OK);
    }

    private Customer getCurrentUser(HttpServletRequest request) {
        String jwt = tokenService.getJwt(request.getHeader("Authorization"));
        String userEmail = tokenService.extractUsername(jwt);
        return  this.customerService.getCustomerByUsername(userEmail);
    }


    @PutMapping("/edit")
    public ResponseEntity<CustomerDto> editCustomer(HttpServletRequest request, @RequestBody CustomerEditDTO customerEdit, BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()){
            throw new BindException(bindingResult);
        }
        Customer customer = this.getCurrentUser(request);
        customer.setFirstName(customerEdit.getFirstName());
        customer.setLastName(customerEdit.getLastName());
        customer.setEmail(customerEdit.getEmail());
        customer.setPhoneNumber(customerEdit.getPhoneNumber());

        this.customerService.editCustomerById(customer.getCustomerId(), customer);

        return  new ResponseEntity<>(this.modelMapper.map(customer,CustomerDto.class),HttpStatus.OK);
    }

    @PutMapping("/edit/address")
    public ResponseEntity<CustomerDto> editCustomerAddress(HttpServletRequest request, @RequestBody CustomerAddressEditDTO customerAddressEditDTO) {
        Customer customer = this.getCurrentUser(request);

        Address address = customer.getAddress();

        address.setNumberAddress(customerAddressEditDTO.getNumberAddress());
        address.setComplementaryNumber(customerAddressEditDTO.getComplementaryNumber());
        address.setStreetName(customerAddressEditDTO.getStreetName());
        address.setComplementaryAddress(customerAddressEditDTO.getComplementaryAddress());

        CityDto cityDto = new CityDto();

        cityDto.setPostalCode(customerAddressEditDTO.getPostalCode());
        cityDto.setCityName(customerAddressEditDTO.getCityName());

        City city = this.findOrCreateCity(cityDto);
        address.setCity(city);

        customer.setAddress(address);

        this.customerService.editCustomerById(customer.getCustomerId(), customer);

        return  new ResponseEntity<>(this.modelMapper.map(customer,CustomerDto.class),HttpStatus.OK);
    }

    private City findOrCreateCity(CityDto cityDto) {
        City existingCity = this.cityService.findCityByCityNameAndPostalCode(cityDto.getPostalCode(), cityDto.getCityName());

        if (existingCity != null) {
            return existingCity;
        } else {
            City newCity = this.modelMapper.map(cityDto, City.class);
            return this.cityService.createCity(newCity);
        }
    }

    @GetMapping("/cities/{param}")
    public ResponseEntity<List<CityDto>> getCitiesForRegister(@PathVariable("param") String param) {
        param += "%";
        List<CityDto> cityDto = new ArrayList<>();
        for (City city : this.cityService.getAllLikeParam(param)) {
            cityDto.add(modelMapper.map(city, CityDto.class));
        }
        return new ResponseEntity<>(cityDto, HttpStatus.OK);
    }

    @PostMapping("/comment/add")
    public ResponseEntity<Boolean> postAComment(@RequestBody @Valid CommentToPost commentToPost, BindingResult bindingResult, HttpServletRequest request) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        Customer customer = getCurrentUser(request);
        this.customerService.postAComment(commentToPost, customer);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}