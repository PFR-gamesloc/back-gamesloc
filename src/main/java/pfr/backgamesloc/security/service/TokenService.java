package pfr.backgamesloc.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import java.util.Map;
import java.util.function.Function;


@Service
@RequiredArgsConstructor
public class TokenService {

    private static final String SECRET_KEY = "1AFSy4ETjbqE2YaUf4EkH9G5JW8WIjgcNN2ZcyxlPOsw/cKJKDsOVXAMRhvXrJpp1njR2aVCt3SY7ve2orsfpHT38D89PFRq7aL9dR0UqL1DDYxukw2lzGFP0u7pazcJT43BhfoOSNxYqg2nlPK4X5IWYcJA/fPDQgPGLVNGe9SPxnaCB6YvxCMRpM+639qqqTgzRkM/zkivolmIK3tggfPKXpL42+GPUSyCfKIPiK1t9cGGWvdV5EfxikMwsmtlN14UeJMAlWt6ed7wW2qF3kgxWgwBQS9MVDV/yVfvYDBZK0bQfOGPLFvbU8M+b1fzep3Au1+gQ+unNzoEHz+jhaaPrXKYM4gqMZFS0WvYp1M=";
    public String generateToken(
            Map<String, Object> extractClaims,
            UserDetails userDetails) {

        LocalDate localDate = LocalDate.now().plusWeeks(1L);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        return Jwts
                .builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(date)
                .signWith(getSignInKey())
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes =Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String getJwt(String authHeader){
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return null;
        }

        return authHeader.substring(7);
    }
}
