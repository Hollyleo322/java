package web.filter;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import web.exception.TokenInvalidException;
import web.model.JwtToken;
import web.provider.JwtProvider;
import web.utility.JwtUtil;

import java.io.IOException;

public class AuthFilter extends GenericFilterBean {

    private final JwtProvider jwtProvider;

    public AuthFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        try {
            String path = httpRequest.getRequestURI();
            if (path.equals("/authorization") || path.equals("/registration") || path.equals("/updateAccessToken")) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                String info = httpRequest.getHeader("Authorization");
                if (info == null) {
                    httpResponse.setStatus(401);
                    httpResponse.getWriter().println("Need authorization, empty field of Authorization");
                }
                else {
                    String token = JwtToken.getToken(info);
                    if (!token.isEmpty()) {
                        if (jwtProvider.validateAccessToken(token)) {
                            Jws<Claims> claims = jwtProvider.getClaims(token);
                            SecurityContext context = SecurityContextHolder.createEmptyContext();
                            Authentication authentication = JwtUtil.buildAuthentication(claims);
                            authentication.setAuthenticated(true);
                            context.setAuthentication(authentication);
                            SecurityContextHolder.setContext(context);
                            filterChain.doFilter(servletRequest, servletResponse);
                        } else {
                            httpResponse.setStatus(401);
                            httpResponse.getWriter().println("Invalid token");
                        }
                    } else {
                        httpResponse.setStatus(401);
                        httpResponse.getWriter().println("Need authorization, empty field of Authorization");
                    }
                }
            }
        }
        catch (Exception e) {
            if (e instanceof TokenInvalidException) {
                httpResponse.setStatus(401);
            }
           httpResponse.getWriter().println(e.getMessage());
        }
    }
}
