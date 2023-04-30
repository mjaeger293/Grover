package htl.steyr.maturabeispiel.models.mapper;

import htl.steyr.maturabeispiel.models.Brand;
import htl.steyr.maturabeispiel.models.Customer;
import htl.steyr.maturabeispiel.models.dto.BrandDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BrandMapper {
    public BrandDTO toDTO(Brand brand) {
        return new BrandDTO(brand.getId(), brand.getName());
    }

    public List<BrandDTO> toDTO(List<Brand> brands) {
        List<BrandDTO> brandDTOs = brands.stream().map(brand ->
                new BrandDTO(
                        brand.getId(),
                        brand.getName())
        ).toList();

        return brandDTOs;
    }
}
