package htl.steyr.maturabeispiel.models.mapper;

import htl.steyr.maturabeispiel.models.Customer;
import htl.steyr.maturabeispiel.models.dto.CustomerDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerMapper {
    public CustomerDTO toDTO(Customer customer) {
        return new CustomerDTO(customer.getId(), customer.getName(), customer.getEmail());
    }

    public List<CustomerDTO> toDTO(List<Customer> customers) {
        List<CustomerDTO> customerDTOs = customers.stream().map(customer ->
                new CustomerDTO(
                        customer.getId(),
                        customer.getName(),
                        customer.getEmail())
        ).toList();

        return customerDTOs;
    }
}
