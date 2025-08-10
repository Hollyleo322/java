package domain.model;

import org.springframework.security.core.GrantedAuthority;

public enum Roles implements GrantedAuthority {
    USER;

    @Override
    public String getAuthority() {
        return Roles.USER.name();
    }
}
