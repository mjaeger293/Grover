package htl.steyr.maturabeispiel.models.mapper;

import htl.steyr.maturabeispiel.models.Model;
import htl.steyr.maturabeispiel.models.Rental;
import htl.steyr.maturabeispiel.models.dto.RentalDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RentalMapper {
    public RentalDTO toDTO(Rental rental) {
        int customerId = rental.getCustomer().getId();
        int modelId = rental.getModel().getId();
        return new RentalDTO(rental.getId(), rental.getFrom(), rental.getTo(), modelId, customerId);
    }

    public List<RentalDTO> toDTO(List<Rental> rentals) {
        List<RentalDTO> rentalDTOs = rentals.stream().map(rental ->
                new RentalDTO(
                        rental.getId(),
                        rental.getFrom(),
                        rental.getTo(),
                        rental.getModel().getId(),
                        rental.getCustomer().getId())
        ).toList();

        return rentalDTOs;
    }
}
