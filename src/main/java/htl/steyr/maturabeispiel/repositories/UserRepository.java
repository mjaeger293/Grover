package htl.steyr.maturabeispiel.repositories;

import htl.steyr.maturabeispiel.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByTokenAndTokenExpirationDateGreaterThan(String token, Date date);

    User findFirstByEmailAndPassword(String email, String password);

    User findFirstByToken(String token);
}
