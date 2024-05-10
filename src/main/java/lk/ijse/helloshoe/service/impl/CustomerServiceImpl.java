package lk.ijse.helloshoe.service.impl;

import lk.ijse.helloshoe.dto.CustomerDTO;
import lk.ijse.helloshoe.entity.Customer;
import lk.ijse.helloshoe.exception.DuplicateException;
import lk.ijse.helloshoe.exception.NotFoundException;
import lk.ijse.helloshoe.repo.CustomerRepo;
import lk.ijse.helloshoe.service.CustomerService;
import lk.ijse.helloshoe.util.GenerateID;
import lk.ijse.helloshoe.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
//@Transactional
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepo customerRepo;
    private final Mapping mapping;
    private final GenerateID generateID;

    @Override
    public boolean saveCustomer(CustomerDTO customerDTO) {
        try {
            customerDTO.setCId(generateID.generateUUID());
            customerRepo.save(mapping.toCustomer(customerDTO));
            return true;

        } catch (Exception e) {
            throw new DuplicateException("Customer Duplicate Details Entered");
        }

    }

    @Override
    public CustomerDTO getCustomer(String cId) {
        if (customerRepo.existsById(cId)) {
            return mapping.toCustomerDTO(customerRepo.getReferenceById(cId));

        }

        throw new NotFoundException("Customer Not Found");

    }

    @Override
    public boolean updateCustomer(CustomerDTO customerDTO) {
        if (customerRepo.existsById(customerDTO.getCId())) {
            try {
                customerRepo.save(mapping.toCustomer(customerDTO));
                return true;

            } catch (Exception e) {
                throw new DuplicateException("Customer Duplicate DAta Entered");

            }

        }

        throw new NotFoundException("Customer Not Found");

    }

    @Override
    public boolean deleteCustomer(String cId) {
        if (customerRepo.existsById(cId)) {
            customerRepo.deleteById(cId);
            return true;

        }

        throw new NotFoundException("Customer Not Found");

    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return mapping.toCustomerDTOList(customerRepo.findAll());

    }
}
