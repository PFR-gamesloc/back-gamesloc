package pfr.backgamesloc.customers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pfr.backgamesloc.customers.DAL.CustomerRepository;
import pfr.backgamesloc.customers.DAL.RoleRepository;
import pfr.backgamesloc.customers.DAL.entities.Customer;
import pfr.backgamesloc.games.DAL.GameRepository;
import pfr.backgamesloc.games.DAL.entities.Game;
import pfr.backgamesloc.shared.controller.DTO.OrderTestDTO;
import pfr.backgamesloc.shared.entities.Order;
import pfr.backgamesloc.shared.repositories.OrderRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CustomerService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private  GameRepository gameRepository;

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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND ,"username" + username + " not found"));
    }


    public List<Customer> getAll(){
        return this.customerRepository.findAllByOrderByCustomerIdAsc();
    }

    public Customer getCustomerByUsername(String username) {
        return this.customerRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("this user is not found"));
    }

    public Customer editCustomerById(Integer customerId, Customer customer) {
        customer.setCustomerId(customerId);
        return this.customerRepository.save(customer);
    }

    public Order createOrderForCustomer(Customer customer, OrderTestDTO orderDTO){

        System.out.println("Création Order");
        Order order = new Order();
        order.setOrderDate(new Date());// Utilisez la date actuelle
        LocalDate localDate = LocalDate.now().plusWeeks(1L);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        order.setReturnDate(date);
        order.setPrice(orderDTO.getPrice());
        order.setCustomer(customer);
        System.out.println(orderDTO);
        List<Game> games = new ArrayList<>();
        for(Integer gameId : orderDTO.getGamesId()){
            Game game = gameRepository.findGameByGameId(gameId);
            games.add(game);

            // Ici je fais comme pour add favorite
            game.getOrders().add(order);
        }

        order.setGames(games);
        System.out.println("Set mes jeux");

        return orderRepository.save(order);
    }
}
