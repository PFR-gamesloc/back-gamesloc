package pfr.backgamesloc.shared.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import pfr.backgamesloc.shared.entities.Order;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Test
    @Sql("findOrderByCustomer_CustomerId.sql")
    public void findOrderByCustomer_CustomerId(){
        List<Order> orders = orderRepository.findOrderByCustomer_CustomerId(1);
        assertEquals(2,orders.size());

    }
}
