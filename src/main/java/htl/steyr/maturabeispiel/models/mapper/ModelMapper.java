package htl.steyr.maturabeispiel.models.mapper;

import htl.steyr.maturabeispiel.models.Model;
import htl.steyr.maturabeispiel.models.dto.ModelDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ModelMapper {
    public ModelDTO toDTO(Model model) {
        int brandId = model.getBrand().getId();
        return new ModelDTO(model.getId(), model.getName(), model.getPrice(), brandId);
    }

    public List<ModelDTO> toDTO(List<Model> models) {
        List<ModelDTO> modelDTOs = models.stream().map(model ->
                new ModelDTO(
                        model.getId(),
                        model.getName(),
                        model.getPrice(),
                        model.getBrand().getId())
        ).toList();

        return modelDTOs;
    }
}
