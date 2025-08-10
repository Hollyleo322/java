package web.provider;

import datasource.model.BlackListOfTokens;
import datasource.model.S21User;
import datasource.repository.BlackListTokenRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import web.exception.TokenInvalidException;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtProvider {

    private final JwtBuilder jwtBuilder =  Jwts.builder();
    private String myKey = "S21secretKey322!S21secretKey322!S21secretKey322!S21secretKey322!";

    private SecretKey key = Keys.hmacShaKeyFor(myKey.getBytes());

    private final BlackListTokenRepository blackListTokenRepository;

    public JwtProvider(BlackListTokenRepository blackListTokenRepository) {
        this.blackListTokenRepository = blackListTokenRepository;
    }


    public String generateAccessToken(S21User user) {
        return "Bearer " + jwtBuilder.claim("uuid", String.valueOf(user.getUuid())).claim("role", user.getRoles()).signWith(key).expiration(new Date(System.currentTimeMillis() + 15 * 60 * 1000)).issuedAt(new Date()).compact();
    }

    public String generateRefreshToken(S21User user) {
        return jwtBuilder.claim("uuid", String.valueOf(user.getUuid())).signWith(key).expiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000)).issuedAt(new Date()).compact();
    }
    public boolean validateAccessToken(String token) throws TokenInvalidException {
        boolean res = false;
        try {
            Jws<Claims> claimsJws = Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            res = true;
        }
        catch (Exception e) {
            throw new TokenInvalidException();
        }
        return res;
    }
    public boolean validateRefreshToken(String token) throws TokenInvalidException {
        boolean res = false;
        boolean isInBlackList = false;
        var blacklist = blackListTokenRepository.findAll();
        for (BlackListOfTokens it : blacklist) {
            if (it.getToken().equals(token)) {
                isInBlackList = true;
                break;
            }
        }
        if (!isInBlackList) {
            try {
                Jws<Claims> claimsJws = Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
                blackListTokenRepository.save(new BlackListOfTokens(token));
                res = true;
            } catch (Exception e) {
                throw new TokenInvalidException();
            }
        }
        return res;
    }
    public Jws<Claims> getClaims(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
    }
}
