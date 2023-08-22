package pfr.backgamesloc.customers.dal;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pfr.backgamesloc.customers.dal.entities.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
    Role findRoleByRoleName(String name);
}
