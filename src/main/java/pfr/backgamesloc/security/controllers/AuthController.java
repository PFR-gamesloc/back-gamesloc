package pfr.backgamesloc.security.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pfr.backgamesloc.customers.DAL.entities.Customer;
import pfr.backgamesloc.security.controllers.DTO.CsrfResponse;
import pfr.backgamesloc.customers.controllers.DTO.CustomerDto;
import pfr.backgamesloc.security.controllers.DTO.LoginForm;



@RestController
@RequestMapping("/auth")
@DependsOn("securityFilterChain")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private ModelMapper modelMapper;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private final RememberMeServices rememberMeServices;

    @PostMapping("/login")
    public CustomerDto login(@Valid @RequestBody LoginForm form,
                             BindingResult bindingResult,
                             HttpServletRequest request,
                             HttpServletResponse response) {
        if (request.getUserPrincipal() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"please, logout first ");
        }

        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"username or password incorrect");
        }

        try {
            request.login(form.getUsername(), form.getPassword());
        } catch (ServletException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"username or password incorrect");
        }

        var auth = (Authentication) request.getUserPrincipal();
        var customer = (Customer) auth.getPrincipal();

        rememberMeServices.loginSuccess(request, response, auth);

        return this.trasnformCustomerToCustomerDTO(customer);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return new ResponseEntity<>("Utilisateur Déconnecté", HttpStatus.OK);
    }

    @GetMapping("/current-user")
    public CustomerDto getCurrentUser(@AuthenticationPrincipal Customer customer) {
        return this.trasnformCustomerToCustomerDTO(customer);
    }

    @GetMapping("/csrf")
    public CsrfResponse csrf(HttpServletRequest request) {
        var csrf = (CsrfToken) request.getAttribute("_csrf");
        return new CsrfResponse(csrf.getToken());
    }

    private CustomerDto trasnformCustomerToCustomerDTO(Customer customer){
        return modelMapper.map(customer, CustomerDto.class);
    }
}

