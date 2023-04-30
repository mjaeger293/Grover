package htl.steyr.maturabeispiel.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {
    public String getAuthorizationHeader(HttpServletRequest request) {
        return request.getHeader(HttpHeaders.AUTHORIZATION);
    }
}
