package pfr.backgamesloc.customers.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import pfr.backgamesloc.customers.services.CustomerService;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;
}
