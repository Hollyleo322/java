package domain.service;

import web.model.SignUpRequest;

public interface UserService {

    String registration(SignUpRequest signuprequest);

    int authorization(String info);
}
