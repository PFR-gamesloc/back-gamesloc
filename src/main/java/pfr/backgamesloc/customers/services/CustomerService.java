package pfr.backgamesloc.customers.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pfr.backgamesloc.customers.DAL.CustomerRepository;
import pfr.backgamesloc.customers.DAL.entities.Customer;
import pfr.backgamesloc.shared.entities.Order;
import pfr.backgamesloc.shared.repositories.OrderRepository;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    public Customer getCustomerById(Integer id) {
         return this.customerRepository.findCustomerByCustomerId(id);
    }

    public List<Order> getOrdersByCustomerId(Integer id) {
        return this.orderRepository.findOrderByCustomer_CustomerId(id);
    }
}
