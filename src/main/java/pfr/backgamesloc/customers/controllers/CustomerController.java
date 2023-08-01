package pfr.backgamesloc.customers.controllers;

import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import pfr.backgamesloc.customers.DAL.entities.Customer;
import pfr.backgamesloc.customers.controllers.DTO.CustomerDto;
import pfr.backgamesloc.customers.services.CustomerService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/customer/{id}")
    public CustomerDto getCustomerById(@PathVariable("id") Integer id) {
        Customer customer = this.customerService.getCustomerById(id);
        return transformCustomerToCustomerDto(customer);
    }

    @GetMapping("/customers")
    public List<CustomerDto> getCustomers() {
        List<Customer> customers = this.customerService.getAll();
        List<CustomerDto> customerDtos = new ArrayList<>();
        for (Customer customer : customers) {
            customerDtos.add(this.transformCustomerToCustomerDto(customer));
        }
        return customerDtos;
    }

    public CustomerDto transformCustomerToCustomerDto(Customer customer) {
        return modelMapper.map(customer, CustomerDto.class);
    }

}
