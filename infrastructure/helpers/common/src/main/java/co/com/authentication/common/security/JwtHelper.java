package co.com.authentication.common.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtHelper {

    private final SecretKey key;

    public JwtHelper() {
        this.key = Keys.hmacShaKeyFor(
                "MySuperSecretKeyThatIsVeryLongAndHasAtLeastSixtyFourCharacters123456".getBytes()
        );
    }

    // Extraer todos los claims del token
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token.replace("Bearer ", "")) // elimina el "Bearer "
                .getBody();
    }

    // Extraer el rol desde el token
    public String getRol(String token) {
        return extractAllClaims(token).get("rolNombre", String.class);
    }

    // Extraer el email desde el token
    public String getEmail(String token) {
        return extractAllClaims(token).get("email", String.class);
    }

    // solicitudes
    public String getDocument(String token) {
        return extractAllClaims(token).get("documento", String.class);
    }


    // Validar si es admin
    public boolean isAdmin(String token) {
        return "admin".equalsIgnoreCase(getRol(token));
    }

    // Validar si es asesor
    public boolean isAsesor(String token) {
        return "asesor".equalsIgnoreCase(getRol(token));
    }

    // Validar si el rol pertenece a una lista
    public boolean hasRole(String token, String... roles) {
        String rol = getRol(token);
        for (String r : roles) {
            if (rol.equalsIgnoreCase(r)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasDocument(String token, String documento) {
        String documentToken = getDocument(token);
        return documentToken.equalsIgnoreCase(documento);
    }
}
