package pfr.backgamesloc.customers.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import org.springframework.web.bind.annotation.*;

import pfr.backgamesloc.customers.DAL.entities.Customer;

import pfr.backgamesloc.customers.controllers.DTO.CustomerDto;

import pfr.backgamesloc.customers.services.CustomerService;
import pfr.backgamesloc.shared.controller.DTO.OrderDTO;
import pfr.backgamesloc.shared.entities.Order;


import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {


    private final CustomerService customerService;

    private final ModelMapper modelMapper;


    @GetMapping("/{id}")
    public CustomerDto getCustomerById(@PathVariable("id") Integer id) {
        Customer customer = this.customerService.getCustomerById(id);
        return transformCustomerToCustomerDto(customer);
    }

    @GetMapping("/{id}/orders")
    public List<OrderDTO> getOrdersByCustomerId(@PathVariable("id") Integer id) {
        List<Order> orders = this.customerService.getOrdersByCustomerId(id);
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (Order order : orders) {
            orderDTOList.add(this.transformOrderToOrderDto(order));
        }
        return orderDTOList;
    }



    private OrderDTO transformOrderToOrderDto(Order order) {
        return modelMapper.map(order, OrderDTO.class);
    }

    public CustomerDto transformCustomerToCustomerDto(Customer customer) {
        return modelMapper.map(customer, CustomerDto.class);
    }
}
