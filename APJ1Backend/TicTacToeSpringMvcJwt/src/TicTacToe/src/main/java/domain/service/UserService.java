package domain.service;

import web.model.JwtRequest;
import web.model.JwtResponse;
import web.model.SignUpRequest;

public interface UserService {

    String registration(SignUpRequest signuprequest);

    JwtResponse authorization(JwtRequest info);
}
