package pfr.backgamesloc.customers.DAL;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pfr.backgamesloc.customers.DAL.entities.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
    Role findRoleByRoleName(String name);
}
