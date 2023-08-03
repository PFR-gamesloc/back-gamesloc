package pfr.backgamesloc.security.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;
import pfr.backgamesloc.security.Service.TokenService;
import pfr.backgamesloc.security.controllers.DTO.LoginForm;
import pfr.backgamesloc.security.controllers.DTO.Token;


@RestController
@RequestMapping("/auth")
@DependsOn("securityFilterChain")
@RequiredArgsConstructor
public class AuthController {

    private final TokenService tokenService;

    @PostMapping("/token")
    public Token token(@RequestBody LoginForm authentication){
        System.out.println(authentication);
        String token = tokenService.generateToken(authentication);
        return new Token(token);
    }

    @GetMapping("/csrf")
    public Token csrf(HttpServletRequest request) {
        var csrf = (CsrfToken) request.getAttribute("_csrf");
        return new Token(csrf.getToken());
    }

}

