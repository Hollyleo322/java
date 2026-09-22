package web.service;

import datasource.model.Roles;
import datasource.repository.UserRepository;
import domain.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import web.exception.LoginIsUnavailable;
import web.exception.TokenInvalidException;
import web.exception.UiidNotFoundException;
import web.model.JwtRequest;
import web.model.JwtResponse;
import web.model.SignUpRequest;
import datasource.model.S21User;
import web.provider.JwtProvider;

import java.util.ArrayList;
import java.util.List;


@Service
public class ServiceAuthorization implements UserService {

    private final UserRepository userRepository;

    private final JwtProvider jwtProvider;


    public ServiceAuthorization(UserRepository userRepository, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public String registration(SignUpRequest signuprequest) {
        boolean check = false;
        for (S21User it : userRepository.findAll())
        {
            if (it.getLogin().equals(signuprequest.getLogin())){
                check = true;
                break;
            }
        }
        if (check) {
            throw new LoginIsUnavailable();
        }
        List<Roles> roles = new ArrayList<>();
        roles.add(Roles.USER);
        userRepository.save(new S21User(signuprequest.getLogin(), signuprequest.getPassword(), roles));
        return signuprequest.getLogin() + " have registered";
    }

    @Override
    public JwtResponse authorization(JwtRequest request) {
        S21User user = null;
        JwtResponse result = null;
        for (S21User it : userRepository.findAll())
        {
            if (it.getLogin().equals(request.getLogin()) && it.getPassword().equals(request.getPassword()))
            {
                user = it;
                break;
            }
        }
        if (user != null) {
            String accessToken = jwtProvider.generateAccessToken(user);
            result = new JwtResponse(accessToken, jwtProvider.generateRefreshToken(user));
        }
        return result;
    }
    public JwtResponse updateAccessToken(String refreshToken) throws TokenInvalidException, UiidNotFoundException {
        JwtResponse result = null;
        if (jwtProvider.validateRefreshToken(refreshToken))
        {
            int uuid = Integer.parseInt((String)jwtProvider.getClaims(refreshToken).getPayload().get("uuid"));
            if (userRepository.existsById(uuid)) {
                result = new JwtResponse(jwtProvider.generateAccessToken(userRepository.findById(uuid).get()), jwtProvider.generateRefreshToken(userRepository.findById(uuid).get()));
            }
            else {
                throw new UiidNotFoundException(uuid);
            }
        }
        else {
            throw new TokenInvalidException();
        }
        return result;
    }
    public JwtResponse updateRefreshToken(String refreshToken) {
        return updateAccessToken(refreshToken);
    }
    public Authentication getAuthentication() {
       return SecurityContextHolder.getContext().getAuthentication();
    }
}
