package htl.steyr.maturabeispiel.repositories;

import htl.steyr.maturabeispiel.models.Model;
import htl.steyr.maturabeispiel.models.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Integer> {

    // Find all models that are not rented in the given period
    // There are three possible cases:
    // 1. The model is rented before the given period started and returned in the given period
    // 2. The model is rented or returned in the given period
    // 3. The model is rented in the given period and returned after the given period ended
    @Query("SELECT m FROM Model m WHERE NOT EXISTS (SELECT r FROM Rental r WHERE r.model = m AND ((r.from <= :from AND r.to >= :to) OR (r.from BETWEEN :from AND :to) OR (r.to BETWEEN :from AND :to)))")
    List<Model> findAllAvailableModels(Date from, Date to);

    // If the model is rented in the given period, it is not available, return false
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN false ELSE true END FROM Rental r WHERE r.model = :model AND ((r.from <= :from AND r.to >= :to) OR (r.from BETWEEN :from AND :to) OR (r.to BETWEEN :from AND :to))")
    boolean isAvailable(Model model, Date from, Date to);

    List<Rental> findAllByClosedIsFalse();
}
