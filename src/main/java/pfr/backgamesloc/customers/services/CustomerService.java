package pfr.backgamesloc.customers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pfr.backgamesloc.customers.DAL.CustomerRepository;
import pfr.backgamesloc.customers.DAL.RoleRepository;
import pfr.backgamesloc.customers.DAL.entities.Customer;
import pfr.backgamesloc.customers.DAL.entities.Role;
import pfr.backgamesloc.shared.entities.Order;
import pfr.backgamesloc.shared.repositories.OrderRepository;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;


    /**
     * Permet de retourner un utilisateur trouvé grâce à son id
     * @param id
     * @return un customer
     */
    public Customer getCustomerById(Integer id) {
         return this.customerRepository.findCustomerByCustomerId(id);
    }


    /**
     * Permet de retourner les commandes par l'id du customer qui les a passés
     * @param id
     * @return la liste des commmandes
     */
    public List<Order> getOrdersByCustomerId(Integer id) {
        return this.orderRepository.findOrderByCustomer_CustomerId(id);
    }

    /**
     * Permet de retrouver un utilisateur grâce a son mail
     * @param email
     * @return l'utilisateur s'il a été trouvé, sinon null
     */
    public Customer findCustomerByMail(String email){
        return this.customerRepository.findByEmail(email).orElse(null);
    }

    /**
     * Méthode a implémenté pour l'authentification de spring security 6.1
     * permet à spring de retrouvé l'utilisateur grâce à son username quand la methode request.login() est appelé
     * @param username
     * @return Les détails d'un utilisateur
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return customerRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found"));
    }


    /**
     * Permet de sauvegarder un utilisateur
     * @param customer l'utilisateur qui va être créé
     * @return l'utilisateur qui vient d'être créé
     */
    public Customer createCustomer(Customer customer) {
        //set l'id du customer a null car problème avec le modelMapper qui récupère l'id de la city
        customer.setCustomerId(null);

        //Encode le password et le set a l'utilisateur qui va être créé
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));

        //récupère le Role User et le set à l'utilisateur qui va être créé
        List<Role> roles = new ArrayList<>();
        roles.add(this.roleRepository.findRoleByRoleName("USER"));
        customer.setRoles(roles);

        //
        return this.customerRepository.save(customer);
    }
    public List<Customer> getAll(){
        return (List<Customer>) this.customerRepository.findAllByOrderByCustomerIdAsc();
    }

    public Customer getCustomerByUsername(String username) {
        return this.customerRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("this user is not found"));
    }

    public Customer editCustomerById(Integer customerId, Customer customer) {
        customer.setCustomerId(customerId);
        return this.customerRepository.save(customer);
    }
}
