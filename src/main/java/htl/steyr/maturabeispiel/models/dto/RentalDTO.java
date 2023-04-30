package htl.steyr.maturabeispiel.models.dto;

import java.util.Date;

public record RentalDTO(int id, Date from, Date to, int modelId, int customerId) {
}
