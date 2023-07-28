package pfr.backgamesloc.customers.controllers;

import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import pfr.backgamesloc.customers.DAL.entities.Customer;
import pfr.backgamesloc.customers.controllers.DTO.CustomerDto;
import pfr.backgamesloc.customers.services.CustomerService;
import pfr.backgamesloc.shared.DTO.OrderDTO;
import pfr.backgamesloc.shared.converters.OrderListToOrderDTOListConverter;
import pfr.backgamesloc.shared.entities.Order;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/customer/{id}")
    public CustomerDto getCustomerById(@PathVariable("id") Integer id){
        Customer customer =  this.customerService.getCustomerById(id);
        return transformCustomerToCustomerDto(customer);
    }

    public CustomerDto transformCustomerToCustomerDto(Customer customer){
        return modelMapper.map(customer, CustomerDto.class);
    }
    @GetMapping("/customer/{id}/orders")
    public List<OrderDTO> getOrdersByCustomerId(@PathVariable("id") Integer id){
        List<Order> orders = this.customerService.getOrdersByCustomerId(id);
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (Order order : orders){
            orderDTOList.add(this.transformOrderToOrderDto(order));
        }
        return orderDTOList;
    }

    public OrderDTO transformOrderToOrderDto(Order order){
        return modelMapper.map(order, OrderDTO.class);
    }
}
