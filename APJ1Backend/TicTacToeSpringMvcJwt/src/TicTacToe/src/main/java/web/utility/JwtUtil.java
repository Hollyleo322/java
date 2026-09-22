package web.utility;

import domain.model.Roles;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.stereotype.Component;
import web.authentication.JwtAuthentication;

import java.util.List;
@Component
public class JwtUtil {
    @SuppressWarnings("unchecked")
    public static JwtAuthentication buildAuthentication (Jws<Claims> claimsJws) {
        return new JwtAuthentication(Integer.parseInt((String) claimsJws.getPayload().get("uuid")), (List<Roles>) claimsJws.getPayload().get("role"));
    }
}
