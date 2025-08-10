package web.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import web.model.SignUpRequest;
import web.service.ServiceAuthorization;

@RestController
public class ControllerAuthorization {

    private final ServiceAuthorization serviceAuthorization;

    public ControllerAuthorization(ServiceAuthorization serviceAuthorization) {
        this.serviceAuthorization = serviceAuthorization;
    }

    @PostMapping("/registration")
    String registration(@RequestBody SignUpRequest signUpRequest) {
        return serviceAuthorization.registration(signUpRequest);
    }

    @PostMapping("/authorization")
    String authorization(@RequestHeader("Authorization") String info) {
      int check = serviceAuthorization.authorization(info);
      String result = "Incorrect login or password";
      if (check != 0 )
      {
          result = "play:" + check;
          result = "Your token is " + result;
      }
      return result;
    }
}
