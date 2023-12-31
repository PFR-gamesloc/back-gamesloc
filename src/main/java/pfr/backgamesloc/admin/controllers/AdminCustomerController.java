package pfr.backgamesloc.admin.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pfr.backgamesloc.customers.dal.entities.Customer;
import pfr.backgamesloc.customers.controllers.dto.CustomerDto;
import pfr.backgamesloc.customers.services.CustomerService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin/customer")
@RequiredArgsConstructor
public class AdminCustomerController {
    private final CustomerService customerService;

    private final ModelMapper modelMapper;

    @GetMapping("/all")
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
