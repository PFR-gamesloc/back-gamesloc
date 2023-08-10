package pfr.backgamesloc.customers.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import org.springframework.web.bind.annotation.*;

import pfr.backgamesloc.customers.DAL.entities.Address;
import pfr.backgamesloc.customers.DAL.entities.Customer;

import pfr.backgamesloc.customers.controllers.DTO.AddressDto;
import pfr.backgamesloc.customers.controllers.DTO.CustomerDto;

import pfr.backgamesloc.customers.services.AddressService;
import pfr.backgamesloc.customers.services.CustomerService;
import pfr.backgamesloc.games.DAL.entities.Game;
import pfr.backgamesloc.games.controllers.DTO.GameDTO;
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
    public AddressDto getAddressOfCurrentUser(HttpServletRequest request){
        Customer customer = this.getCurrentUser(request);
        Address address = this.addressService.findAddressByCustomerUsername(customer.getEmail());
        return modelMapper.map(address, AddressDto.class);
    }

    @GetMapping("/me/favs")
    public List<GameLikeDto> getLikesOfCurrentUser(HttpServletRequest request){
        Customer customer = this.getCurrentUser(request);
        List<Game> games = this.gameService.findFavsByCustomerId(customer.getCustomerId());
        List<GameLikeDto> gameDTOList = new ArrayList<>();
        for (Game game : games) {
            gameDTOList.add(this.transformGameTOGameDTO(game));
        }
        return gameDTOList;
    }


    private OrderDTO transformOrderToOrderDto(Order order) {
        return modelMapper.map(order, OrderDTO.class);
    }

    private CustomerDto transformCustomerToCustomerDto(Customer customer) {
        return modelMapper.map(customer, CustomerDto.class);
    }

    private Customer getCurrentUser(HttpServletRequest request){
        String jwt = tokenService.getJwt(request.getHeader("Authorization"));
        String userEmail = tokenService.extractUsername(jwt);
        return this.customerService.getCustomerByUsername(userEmail);
    }

    public GameLikeDto transformGameTOGameDTO(Game game) {
        return this.modelMapper.map(game, GameLikeDto.class);
    }
}
