package htl.steyr.maturabeispiel.controllers;

import htl.steyr.maturabeispiel.annotations.CheckUserAuthorization;
import htl.steyr.maturabeispiel.annotations.ResetValidityTime;
import htl.steyr.maturabeispiel.models.Customer;
import htl.steyr.maturabeispiel.models.Model;
import htl.steyr.maturabeispiel.models.Rental;
import htl.steyr.maturabeispiel.models.dto.*;
import htl.steyr.maturabeispiel.models.mapper.ModelMapper;
import htl.steyr.maturabeispiel.models.mapper.RentalMapper;
import htl.steyr.maturabeispiel.repositories.CustomerRepository;
import htl.steyr.maturabeispiel.repositories.ModelRepository;
import htl.steyr.maturabeispiel.repositories.RentalRepository;
import htl.steyr.maturabeispiel.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

import static java.lang.Math.ceil;

@RestController
@RequestMapping("/rental")
@CrossOrigin
public class RentalController {

    @Autowired
    RentalRepository rentalRepository;

    @Autowired
    ModelRepository modelRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    RentalMapper rentalMapper;

    @GetMapping("/period/{from}/{to}")
    @CheckUserAuthorization
    @ResetValidityTime
    public List<ModelDTO> getAvailableModelsInPeriod(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date from, // @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                     @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date to) {

        if (from.after(to)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        List<Model> models = rentalRepository.findAllAvailableModels(from, to);

        return modelMapper.toDTO(models);

    }

    @PostMapping("/rent")
    @CheckUserAuthorization
    @ResetValidityTime
    public void rentModel(@RequestBody RentalDTO rentalDTO) {

        Date from = rentalDTO.from();
        Date to = rentalDTO.to();

        // Change the time to 23:59:59. Otherwise, it would be 00:00:00
        to.setHours(23);
        to.setMinutes(59);
        to.setSeconds(59);


        // Check if the rental date is before the return date
        if (from.after(to)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Model model = modelRepository.findById(rentalDTO.modelId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        // Check if the model is available in the given period
        if (!rentalRepository.isAvailable(model, rentalDTO.from(), rentalDTO.to())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Customer customer = customerRepository.findById(rentalDTO.customerId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        Rental rental = new Rental(from, to, model, customer);

        rentalRepository.save(rental);
    }

    @GetMapping("/customer")
    @CheckUserAuthorization
    @ResetValidityTime
    public List<RentalDTO> getAllOpenRentals() {

        List<Rental> rentals = rentalRepository.findAllByClosedIsFalse();

        return rentalMapper.toDTO(rentals);

    }

    @PostMapping("/done/{rentalId}")
    @CheckUserAuthorization
    @ResetValidityTime
    public TotalPriceDTO closeRental(@PathVariable("rentalId") int rentalId,
                                     @RequestBody LoadingsDTO loadingsDTO) {

        if (loadingsDTO.loadings() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Rental rental = rentalRepository.findById(rentalId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        rental.setLoadings(loadingsDTO.loadings());
        rental.setClosed(true);

        rentalRepository.save(rental);

        // Get date difference from rental in days. It needs to be round up to get the correct number of days
        int days = (int) ceil((rental.getTo().getTime() - rental.getFrom().getTime()) / (1000.0 * 60 * 60 * 24));

        // Calculate total price (days * price per day) + (loadings * 0.05)
        double totalPrice = days * rental.getModel().getPrice() + loadingsDTO.loadings() * 0.05;

        return new TotalPriceDTO(totalPrice);
    }

}
