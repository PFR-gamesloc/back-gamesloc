package pfr.backgamesloc.customers.DAL;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import pfr.backgamesloc.customers.DAL.entities.Customer;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;


    @Test
    @Sql("customerBDDTest.sql")
    public void customerFindEmail_True() {
        Optional<Customer> customer = customerRepository.findByEmail("john.doe@example.com");
        assertTrue(customer.isPresent());
        assertThat(customer.get().getFirstName()).isEqualTo("John");
    }

    @Test
    @Sql("CustomerBDDTestFindAll.sql")
    public void customerFindAlllByDesc(){
        List<Customer> customers = customerRepository.findAllByOrderByCustomerIdAsc();
        assertThat(customers).isNotNull();

        long previousId = Long.MIN_VALUE;
        for (Customer customer : customers) {
            assertThat(customer.getCustomerId()).isGreaterThan((int) previousId);
            previousId = customer.getCustomerId();
        }
    }

    @Test
    @Sql("CustomerBDDTestFindByPhoneNumber.sql")
        public void customerFindFindByPhoneNumber(){
        Optional<Customer> customer = customerRepository.findByPhoneNumber("0628282828");
        assertTrue(customer.isPresent());
        assertThat(customer.get().getFirstName()).isEqualTo("Peter");
    }

}
