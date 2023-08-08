package pfr.backgamesloc.security.controllers;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pfr.backgamesloc.customers.services.CustomerService;
import pfr.backgamesloc.security.Service.AuthService;
import pfr.backgamesloc.security.Service.TokenService;
import pfr.backgamesloc.security.controllers.DTO.AuthRequest;
import pfr.backgamesloc.security.controllers.DTO.RegisterRequest;
import pfr.backgamesloc.security.controllers.DTO.Token;


@RestController
@RequestMapping("/auth")
@DependsOn("securityFilterChain")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    private final TokenService tokenService;

    private final CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<Token> register(
            @RequestBody RegisterRequest request
            ) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Token> token(
            @RequestBody AuthRequest request){
        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping("/is-authenticated")
    public ResponseEntity<Boolean> isAuthenticated(HttpServletRequest request){
        String jwt = tokenService.getJwt(request.getHeader("Authorization"));
        String userEmail = tokenService.extractUsername(jwt);
        UserDetails user = customerService.loadUserByUsername(userEmail);
        return ResponseEntity.ok(tokenService.isTokenValid(jwt, user));
    }
}

