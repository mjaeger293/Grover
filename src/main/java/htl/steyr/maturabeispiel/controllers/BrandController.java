package htl.steyr.maturabeispiel.controllers;

import htl.steyr.maturabeispiel.annotations.CheckUserAuthorization;
import htl.steyr.maturabeispiel.annotations.ResetValidityTime;
import htl.steyr.maturabeispiel.models.Brand;
import htl.steyr.maturabeispiel.models.dto.BrandDTO;
import htl.steyr.maturabeispiel.models.mapper.BrandMapper;
import htl.steyr.maturabeispiel.repositories.BrandRepository;
import htl.steyr.maturabeispiel.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    BrandMapper brandMapper;

    /**
     * CheckUserAuthorization and ResetValidityTime get the Authorization header inside the aspect
     * or else every method would need to have the token as the first parameter.
     */
    @PostMapping("/create")
    @CheckUserAuthorization
    @ResetValidityTime
    public void createBrand(/*@RequestHeader(HttpHeaders.AUTHORIZATION) String token,*/
                            @RequestBody BrandDTO brandDTO) {

        String name = brandDTO.name();

        if (name == null || name.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        brandRepository.save(new Brand(name));
    }

    @PostMapping("/update")
    @CheckUserAuthorization
    @ResetValidityTime
    public void updateBrand(@RequestBody BrandDTO brandDTO) {

        int brandId = brandDTO.id();
        String newName = brandDTO.name();

        if (newName == null || newName.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Brand brand = brandRepository.findById(brandId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        brand.setName(newName);

        brandRepository.save(brand);
    }

    @DeleteMapping("/delete/{id}")
    @CheckUserAuthorization
    @ResetValidityTime
    public void deleteBrand(@PathVariable("id") int id) {

        Brand brand = brandRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        brandRepository.delete(brand);
    }

    @GetMapping("/load")
    @CheckUserAuthorization
    @ResetValidityTime
    public List<BrandDTO> getBrands(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {

        List<Brand> brands = brandRepository.findAll();

        return brandMapper.toDTO(brands);
    }

    @GetMapping("/load/{id}")
    @CheckUserAuthorization
    @ResetValidityTime
    public BrandDTO getBrand(@PathVariable("id") int id) {

        Brand brand = brandRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        return brandMapper.toDTO(brand);
    }


}
