package htl.steyr.maturabeispiel.controllers;

import htl.steyr.maturabeispiel.annotations.CheckUserAuthorization;
import htl.steyr.maturabeispiel.annotations.ResetValidityTime;
import htl.steyr.maturabeispiel.models.Customer;
import htl.steyr.maturabeispiel.models.dto.CustomerDTO;
import htl.steyr.maturabeispiel.models.mapper.CustomerMapper;
import htl.steyr.maturabeispiel.repositories.CustomerRepository;
import htl.steyr.maturabeispiel.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerMapper customerMapper;

    @PostMapping("/create")
    @CheckUserAuthorization
    @ResetValidityTime
    public void createCustomer(@RequestBody CustomerDTO customerDTO) {

        String name = customerDTO.name();
        String email = customerDTO.email();

        if (name == null || name.isEmpty() || email == null || email.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (customerRepository.existsByEmail(customerDTO.email())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        customerRepository.save(new Customer(name, email));
    }

    @PostMapping("/update")
    @CheckUserAuthorization
    @ResetValidityTime
    public void updateCustomer(@RequestBody CustomerDTO customerDTO) {

        int customerId = customerDTO.id();
        String newName = customerDTO.name();
        String newEmail = customerDTO.email();

        if (newName == null || newName.isEmpty() || newEmail == null || newEmail.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        customer.setName(newName);
        customer.setEmail(newEmail);

        customerRepository.save(customer);
    }

    @DeleteMapping("/delete/{id}")
    @CheckUserAuthorization
    @ResetValidityTime
    public void deleteCustomer(@PathVariable("id") int id) {

        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        customerRepository.delete(customer);
    }

    @GetMapping("/load")
    @CheckUserAuthorization
    @ResetValidityTime
    public List<CustomerDTO> loadCustomers() {
        List<Customer> customers = customerRepository.findAll();

        return customerMapper.toDTO(customers);
    }

    @GetMapping("/load/{id}")
    @CheckUserAuthorization
    @ResetValidityTime
    public CustomerDTO loadCustomer(@PathVariable("id") int id) {

        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        return customerMapper.toDTO(customer);
    }
}
