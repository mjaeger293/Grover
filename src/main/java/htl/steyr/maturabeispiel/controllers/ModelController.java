package htl.steyr.maturabeispiel.controllers;

import htl.steyr.maturabeispiel.annotations.CheckUserAuthorization;
import htl.steyr.maturabeispiel.annotations.ResetValidityTime;
import htl.steyr.maturabeispiel.models.Brand;
import htl.steyr.maturabeispiel.models.Model;
import htl.steyr.maturabeispiel.models.dto.ModelDTO;
import htl.steyr.maturabeispiel.models.mapper.ModelMapper;
import htl.steyr.maturabeispiel.repositories.BrandRepository;
import htl.steyr.maturabeispiel.repositories.ModelRepository;
import htl.steyr.maturabeispiel.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/model")
public class ModelController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelRepository modelRepository;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    ModelMapper modelMapper;


    @PostMapping("/create")
    @CheckUserAuthorization
    @ResetValidityTime
    public void createBrand(@RequestBody ModelDTO modelDTO) {

        String name = modelDTO.name();
        double price = modelDTO.price();
        int brandId = modelDTO.brand();

        if (name == null || name.isEmpty() || price <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Brand brand = brandRepository.findById(brandId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        modelRepository.save(new Model(name, price, brand));
    }

    @PostMapping("/update")
    @CheckUserAuthorization
    @ResetValidityTime
    public void updateBrand(@RequestBody ModelDTO modelDTO) {

        String newName = modelDTO.name();
        double newPrice = modelDTO.price();
        int modelId = modelDTO.id();
        int newBrandId = modelDTO.brand();

        if (newName == null || newName.isEmpty() || newPrice <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Model model = modelRepository.findById(modelId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        model.setName(newName);
        model.setPrice(newPrice);

        Brand brand = brandRepository.findById(newBrandId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        model.setBrand(brand);

        modelRepository.save(model);
    }

    @DeleteMapping("/delete/{id}")
    @CheckUserAuthorization
    @ResetValidityTime
    public void deleteBrand(@PathVariable("id") int id) {

        Model model = modelRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        modelRepository.delete(model);
    }

    @GetMapping("/load")
    @CheckUserAuthorization
    @ResetValidityTime
    public List<ModelDTO> getBrands() {

        List<Model> models = modelRepository.findAll();

        return modelMapper.toDTO(models);
    }

    @GetMapping("/load/{id}")
    @CheckUserAuthorization
    @ResetValidityTime
    public ModelDTO getBrand(@PathVariable("id") int id) {

        Model model = modelRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        return modelMapper.toDTO(model);
    }

    @GetMapping("/brand/{brandId}")
    @CheckUserAuthorization
    @ResetValidityTime
    public List<ModelDTO> getModelsByBrand(@PathVariable("brandId") int brandId) {

        Brand brand = brandRepository.findById(brandId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        List<Model> models = modelRepository.findAllByBrand(brand);

        return modelMapper.toDTO(models);
    }

}
