package web.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import web.exception.TokenInvalidException;
import web.exception.UserNotFoundException;
import web.model.*;
import web.provider.JwtProvider;
import web.service.ServiceAuthorization;

@RestController
public class ControllerAuthorization {

    private final ServiceAuthorization serviceAuthorization;

    private final JwtProvider jwtProvider;

    public ControllerAuthorization(ServiceAuthorization serviceAuthorization, JwtProvider jwtProvider) {
        this.serviceAuthorization = serviceAuthorization;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/registration")
    String registration(@RequestBody SignUpRequest signUpRequest) {
        return serviceAuthorization.registration(signUpRequest);
    }

    @PostMapping("/authorization")
    JwtResponse authorization(@RequestBody JwtRequest request) {
      JwtResponse check = serviceAuthorization.authorization(request);
      if (check == null )
      {
        throw new UserNotFoundException("Incorrect login or password");
      }
      return check;
    }
    @PostMapping("/updateAccessToken")
    String updateAccessToken(@RequestBody RefreshJwtRequest refreshToken) {
        String result = "";
        JwtResponse response = serviceAuthorization.updateAccessToken(refreshToken.getRefreshToken());
        result = response.getAccessToken();
        if (result.isEmpty())
        {
            throw new TokenInvalidException();
        }
        return result;
    }
    @PostMapping("/updateRefreshToken")
    String updateRefreshToken(@RequestBody RefreshJwtRequest refreshToken) {
        return "Need authorization";
    }

    @PostMapping("/updateWrongRefreshToken")
    String updateWrongRefreshToken(@RequestBody RefreshJwtRequest refreshToken) {
        String result = "";
        JwtResponse response = serviceAuthorization.updateRefreshToken(refreshToken.getRefreshToken());
        result = response.getRefreshToken();
        if (result.isEmpty())
        {
            throw new TokenInvalidException();
        }
        return result;
    }
}
