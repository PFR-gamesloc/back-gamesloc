package pfr.backgamesloc.customers.DAL;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pfr.backgamesloc.customers.DAL.entities.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Customer findCustomerByCustomerId(Integer id);
}
