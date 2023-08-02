package pfr.backgamesloc.customers.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;
import pfr.backgamesloc.customers.DAL.entities.Address;
import pfr.backgamesloc.customers.DAL.entities.City;
import pfr.backgamesloc.customers.DAL.entities.Customer;
import pfr.backgamesloc.customers.controllers.DTO.AddressDto;
import pfr.backgamesloc.customers.controllers.DTO.CustomerToCreateDto;
import pfr.backgamesloc.customers.controllers.DTO.CustomerDto;
import pfr.backgamesloc.customers.services.AddressService;
import pfr.backgamesloc.customers.services.CityService;
import pfr.backgamesloc.customers.services.CustomerService;
import pfr.backgamesloc.shared.DTO.OrderDTO;
import pfr.backgamesloc.shared.entities.Order;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CityService cityService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/api/create-customer")
    public CustomerDto createCustomer(@Valid @RequestBody CustomerToCreateDto customerToCreate) {

        Customer checker = this.customerService.findCustomerByMail(customerToCreate.getEmail());

        if (checker == null) {

            Customer customer = modelMapper.map(customerToCreate, Customer.class);
            City city = this.cityService.findCityById(customerToCreate.getCityId());

            if (city == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "the city doesn't exist");
            }

            Address address = modelMapper.map(customerToCreate, Address.class);
            address.setCity(city);

            if (this.addressService.findIfAddressAddressAlreadyExist(address) == null) {
                this.addressService.save(address);
            }
            customer.setAddress(address);

            Customer customerCreated = this.customerService.createCustomer(customer);

            return modelMapper.map(customerCreated,CustomerDto.class);
        }
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "the user already exist with the email: " + customerToCreate.getEmail() );
        }
    }


    @GetMapping("/customer/{id}")
    public CustomerDto getCustomerById(@PathVariable("id") Integer id) {
        Customer customer = this.customerService.getCustomerById(id);
        return transformCustomerToCustomerDto(customer);
    }

    public CustomerDto transformCustomerToCustomerDto(Customer customer) {
        return modelMapper.map(customer, CustomerDto.class);
    }

    @GetMapping("/customer/{id}/orders")
    public List<OrderDTO> getOrdersByCustomerId(@PathVariable("id") Integer id) {
        List<Order> orders = this.customerService.getOrdersByCustomerId(id);
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (Order order : orders) {
            orderDTOList.add(this.transformOrderToOrderDto(order));
        }
        return orderDTOList;
    }

    public OrderDTO transformOrderToOrderDto(Order order) {
        return modelMapper.map(order, OrderDTO.class);
    }
}
