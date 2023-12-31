package pfr.backgamesloc.security.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pfr.backgamesloc.customers.services.CustomerService;
import pfr.backgamesloc.security.service.AuthService;
import pfr.backgamesloc.security.service.TokenService;
import pfr.backgamesloc.security.controllers.dto.AuthRequest;
import pfr.backgamesloc.security.controllers.dto.RegisterRequest;
import pfr.backgamesloc.security.controllers.dto.TokenResponse;


@RestController
@RequestMapping("/auth")
@DependsOn("securityFilterChain")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    private final TokenService tokenService;

    private final CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<Boolean> register(@RequestBody @Valid RegisterRequest request, BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        return ResponseEntity.ok(service.register(request));

    }

    @PostMapping("/authenticate")
    public ResponseEntity<TokenResponse> token(@RequestBody @Valid AuthRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Problèmes lors de la connexion.");
        }
        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping("/is-authenticated")
    public ResponseEntity<Boolean> isAuthenticated(HttpServletRequest request) {
        if (request.getHeader("Authorization") == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Aucun token");
        }
        String jwt = tokenService.getJwt(request.getHeader("Authorization"));
        String userEmail = tokenService.extractUsername(jwt);
        UserDetails user = customerService.loadUserByUsername(userEmail);
        return ResponseEntity.ok(tokenService.isTokenValid(jwt, user));
    }
}

