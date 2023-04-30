package htl.steyr.maturabeispiel.repositories;

import htl.steyr.maturabeispiel.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    boolean existsByEmail(String email);
}
