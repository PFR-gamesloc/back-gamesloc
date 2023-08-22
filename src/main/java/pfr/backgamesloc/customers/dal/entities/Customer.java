package pfr.backgamesloc.customers.dal.entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pfr.backgamesloc.games.dal.entities.Game;
import pfr.backgamesloc.shared.entities.Opinion;
import pfr.backgamesloc.shared.entities.Order;

import java.util.*;

@Entity
@Table(name = "customer")
@Data
public class Customer implements UserDetails {

    @Id
    @Column(name = "customer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerId;

    @Column(name = "first_name")
    @Size(min=2, max = 50)
    private String firstName;

    @Column(name = "last_name")
    @Size(min=2, max = 50)
    private String lastName;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Size(min = 10, max = 10)
    @Column(name = "phone_number")
    private String phoneNumber;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "customer")
    private List<Order> orders;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "customer")
    private List<Opinion> opinions;

    @ManyToMany(mappedBy = "customersLike")
    @JsonManagedReference
    private List<Game> favoriteGames;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "customer_role",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for(Role role : this.roles){
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}