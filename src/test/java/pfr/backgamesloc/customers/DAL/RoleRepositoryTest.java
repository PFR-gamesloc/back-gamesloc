package pfr.backgamesloc.customers.DAL;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import pfr.backgamesloc.customers.DAL.entities.Role;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    @Sql("roleBDDTest.sql")
    public void customerFindRoleByRoleName() {
        Role role =  roleRepository.findRoleByRoleName("USER");
        assertThat(role.getRoleName()).isEqualTo("USER");
    }

}
