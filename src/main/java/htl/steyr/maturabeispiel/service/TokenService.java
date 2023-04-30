package htl.steyr.maturabeispiel.service;

import htl.steyr.maturabeispiel.models.User;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class TokenService {
    public String generateHash(User user) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update((user.getId() + user.getEmail() + System.currentTimeMillis()).getBytes());
        byte[] digiest = messageDigest.digest();

        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < digiest.length; i++) {
            hexString.append(Integer.toHexString(0xFF & digiest[i]));
        }

        return hexString.toString();
    }
}
