package pfr.backgamesloc.security.controllers.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data @RequiredArgsConstructor
@AllArgsConstructor
public class AuthRequest {

    private String username;

    private String password;
}
