package pfr.backgamesloc.customers.DAL;

import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pfr.backgamesloc.customers.DAL.entities.Customer;
import pfr.backgamesloc.games.DAL.entities.Game;

import java.util.List;
import java.util.Optional;
@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Customer findCustomerByCustomerId(Integer id);
    Optional<Customer> findByEmail(String userName);
    List<Customer> findAllByOrderByCustomerIdAsc();

    Optional<Customer> findByPhoneNumber(String phoneNumber);
}
