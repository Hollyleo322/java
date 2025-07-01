package web.authentication;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import domain.model.Roles;


import java.util.Collection;
import java.util.List;
public class JwtAuthentication implements Authentication {

    private Integer uuid;

    private List<Roles> roles;

    private boolean isAuthenticated;

    public JwtAuthentication(Integer uuid, List<Roles> roles) {
        this.uuid = uuid;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return uuid;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return String.valueOf(uuid);
    }
}
