package pfr.backgamesloc.customers.dal;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pfr.backgamesloc.customers.dal.entities.Customer;

import java.util.List;
import java.util.Optional;
@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Optional<Customer> findByEmail(String userName);
    List<Customer> findAllByOrderByCustomerIdAsc();
    Optional<Customer> findByPhoneNumber(String phoneNumber);
}
