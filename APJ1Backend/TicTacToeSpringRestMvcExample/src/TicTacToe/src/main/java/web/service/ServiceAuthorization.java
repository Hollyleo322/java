package web.service;

import datasource.repository.UserRepository;
import domain.service.UserService;
import org.springframework.stereotype.Service;
import web.model.SignUpRequest;
import datasource.model.S21User;

import java.util.Base64;

@Service
public class ServiceAuthorization implements UserService {

    private final UserRepository userRepository;

    public ServiceAuthorization(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String registration(SignUpRequest signuprequest) {
        userRepository.save(new S21User(signuprequest.getLogin(), signuprequest.getPassword()));
        return signuprequest.getLogin() + " have registered";
    }

    @Override
    public int authorization(String info) {
        int res = 0;
        byte[] bytes = Base64.getDecoder().decode(info);
        info = new String(bytes);
        String[] loginpassword = info.split(":");
        String login = loginpassword[0];
        String password = loginpassword[1];
        for (S21User user : userRepository.findAll())
        {
            if (user.getLogin().equals(login) && user.getPassword().equals(password))
            {
                res =  user.getUuid();
                break;
            }
        }
        return res;
    }
}
