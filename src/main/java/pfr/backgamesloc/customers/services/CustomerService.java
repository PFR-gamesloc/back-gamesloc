package pfr.backgamesloc.customers.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NonUniqueResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pfr.backgamesloc.customers.DAL.CustomerRepository;
import pfr.backgamesloc.customers.DAL.entities.Customer;
import pfr.backgamesloc.customers.controllers.DTO.CommentToPost;
import pfr.backgamesloc.games.DAL.GameRepository;
import pfr.backgamesloc.games.DAL.entities.Game;
import pfr.backgamesloc.shared.entities.Opinion;
import pfr.backgamesloc.shared.entities.Order;
import pfr.backgamesloc.shared.repositories.OpinionRepository;
import pfr.backgamesloc.shared.repositories.OrderRepository;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService implements UserDetailsService {


    private final CustomerRepository customerRepository;

    private final OrderRepository orderRepository;

    private final GameRepository gameRepository;

    private final OpinionRepository opinionRepository;
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

    public void postAComment(CommentToPost commentToPost, Customer customer) {
        Game game = gameRepository.findById(commentToPost.getGameId())
                .orElseThrow(() -> new EntityNotFoundException("Game not found"));

        List<Order> orders = orderRepository.findOrderByCustomer_CustomerId(customer.getCustomerId());

        List<Opinion> opinions = opinionRepository.findOpinionsByCustomer_customerId(customer.getCustomerId());


        for (Opinion opinion : opinions){
            if (opinion.getCustomer().getCustomerId() == customer.getCustomerId() && opinion.getGame().getGameId() == game.getGameId()){
                throw new NonUniqueResultException("User already commented this game");
            }
        }

        boolean gameFind = false;

        for (Order order : orders){
            for ( Game gameOrdered: order.getGames()){
                if (gameOrdered.getGameId() == game.getGameId()) {
                    gameFind = true;
                    break;
                }
            }
        }

        if (!gameFind){
            throw new EntityNotFoundException("this user didn't ordered this game");
        }

        Opinion opinion = new Opinion();

        opinion.setCustomer(customer);
        opinion.setComment(commentToPost.getComment());
        opinion.setGame(game);
        opinion.setPublishDate(new Date());
        opinion.setRating(commentToPost.getRating());

        this.opinionRepository.save(opinion);
    }
}
