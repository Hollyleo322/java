package web.filter;

import datasource.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

public class AuthFilter extends GenericFilterBean {

    private final UserRepository userRepository;

    public AuthFilter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        try {
            String path = httpRequest.getRequestURI();
            if (path.equals("/authorization") || path.equals("/registration")) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                String info = httpRequest.getHeader("Authorization");
                if (info != null) {
                    if (isValid(info)) {
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
        catch (Exception e) {
            httpResponse.getWriter().println(e.getMessage());
        }
    }
    private boolean isValid(String info) {
        boolean res = false;
        try {
            String [] array = info.split(":");
            String token = array[0];
            String id = array[1];
            if (token.equals("play") && userRepository.existsById(Integer.parseInt(id)))
            {
                res = true;
            }
        }
        catch (Exception e) {
            throw new RuntimeException("token isn't correct");
        }
        return res;
    }

}
