package lk.ijse.helloshoe.service;

import lk.ijse.helloshoe.dto.CustomerDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CustomerService {
    boolean saveCustomer(CustomerDTO customerDTO);
    CustomerDTO getCustomer(String cId);
    boolean updateCustomer(CustomerDTO customerDTO);
    boolean deleteCustomer(String cId);
    List<CustomerDTO> getAllCustomers();

}
