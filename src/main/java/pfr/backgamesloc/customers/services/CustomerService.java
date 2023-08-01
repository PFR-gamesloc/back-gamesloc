package pfr.backgamesloc.customers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pfr.backgamesloc.customers.DAL.CustomerRepository;
import pfr.backgamesloc.customers.DAL.entities.Customer;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer getCustomerById(Integer id) {
         return this.customerRepository.findCustomerByCustomerId(id);
    }

    public List<Customer> getAll(){
        return (List<Customer>) this.customerRepository.findAll();
    }


}
