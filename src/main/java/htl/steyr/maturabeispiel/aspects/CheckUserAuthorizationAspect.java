package htl.steyr.maturabeispiel.aspects;

import htl.steyr.maturabeispiel.repositories.UserRepository;
import htl.steyr.maturabeispiel.service.AuthorizationService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@Aspect
@Component
public class CheckUserAuthorizationAspect {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthorizationService authorizationService;

    @Before("@annotation(htl.steyr.maturabeispiel.annotations.CheckUserAuthorization)")
    public void checkUserAuthorization() {
        // Get the bearer-token from the request authorization header.
        // Else, the controller methods would need to have the token as an injected parameter.
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String token = authorizationService.getAuthorizationHeader(request);

        token = token.replace("Bearer ", "");

        // If the validation date is in the past, the token is invalid and an exception is thrown.
        // The initial method will not be executed.
        if (!userRepository.existsByTokenAndTokenExpirationDateGreaterThan(token, new Date(System.currentTimeMillis()))) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }
}
