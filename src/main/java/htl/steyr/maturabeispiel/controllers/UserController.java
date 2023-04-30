package htl.steyr.maturabeispiel.controllers;

import htl.steyr.maturabeispiel.models.User;
import htl.steyr.maturabeispiel.repositories.UserRepository;
import htl.steyr.maturabeispiel.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

@CrossOrigin
@RestController
public class UserController {

    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        User loginUser = userRepository.findFirstByEmailAndPassword(user.getEmail(), user.getPassword());

        if (loginUser == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        String token;

        try {
            token = tokenService.generateHash(loginUser);
        } catch (NoSuchAlgorithmException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        Date tokenExpirationDate = new Date(System.currentTimeMillis() + (10 * 60 * 1000));

        loginUser.setToken(token);
        loginUser.setTokenExpirationDate(tokenExpirationDate);
        userRepository.save(loginUser);

        return "\"" + token + "\"";
    }
}
