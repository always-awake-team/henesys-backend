package henesys.henesysbackend.security.jwt;

import henesys.henesysbackend.member.domain.enumtype.RoleType;
import henesys.henesysbackend.util.JwtUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class Jwt {

    private final String AUTHORIZATION = "Authorization";
    private final String AUTHORIZATION_KEY = "role";
    private final String BEARER_PREFIX = "Barer ";
    private final long EXPIRATION_DATE = 60 * 60 * 1000L;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key = createKey();

    private Key createKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // JWT 생성
    public String creatToken(String name, RoleType role) {
        Date date = new Date(System.currentTimeMillis());

        return Jwts.builder()
                .setSubject(name)
                .claim(AUTHORIZATION_KEY, role)
                .setExpiration(new Date(date.getTime() + EXPIRATION_DATE))
                .setIssuedAt(date)
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    // JWT 검증
    public boolean isValidateToken(String token) {
        String substringToken = JwtUtil.substringToken(token);

        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(substringToken);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
        } catch (ExpiredJwtException e) {
        } catch (UnsupportedJwtException e) {
        } catch (IllegalArgumentException e) {
        }

        return false;
    }

    // JWT 에서 UserDetails


}
