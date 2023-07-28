package pfr.backgamesloc.customers.DAL;

import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import pfr.backgamesloc.customers.DAL.entities.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    @Transactional
    Customer findCustomerByCustomerId(Integer id);
}
