package htl.steyr.maturabeispiel.aspects;

import htl.steyr.maturabeispiel.models.User;
import htl.steyr.maturabeispiel.repositories.UserRepository;
import htl.steyr.maturabeispiel.service.AuthorizationService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;

@Aspect
@Component
public class ResetValidityTimeAspect {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthorizationService authorizationService;

    @AfterReturning("@annotation(htl.steyr.maturabeispiel.annotations.ResetValidityTime)")
    public void resetTime() {
        //String token = (String) joinPoint.getArgs()[0];
        // Get the bearer-token from the request authorization header.
        // Else, the controller methods would need to have the token as an injected parameter.
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String token = authorizationService.getAuthorizationHeader(request);

        token = token.replace("Bearer ", "");

        User user = userRepository.findFirstByToken(token);

        Date tokenExpirationDate = new Date(System.currentTimeMillis() + (10 * 60 * 1000));
        user.setTokenExpirationDate(tokenExpirationDate);

        userRepository.save(user);
    }
}
