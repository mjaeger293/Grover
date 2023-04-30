package htl.steyr.maturabeispiel.repositories;

import htl.steyr.maturabeispiel.models.Brand;
import htl.steyr.maturabeispiel.models.Model;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModelRepository extends JpaRepository<Model, Integer> {
    List<Model> findAllByBrand(Brand brand);
}
