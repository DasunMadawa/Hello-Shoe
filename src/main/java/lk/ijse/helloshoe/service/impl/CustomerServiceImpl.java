package lk.ijse.helloshoe.service.impl;

import lk.ijse.helloshoe.dto.CustomerDTO;
import lk.ijse.helloshoe.dto.EmailDTO;
import lk.ijse.helloshoe.entity.Customer;
import lk.ijse.helloshoe.exception.DuplicateException;
import lk.ijse.helloshoe.exception.NotFoundException;
import lk.ijse.helloshoe.repo.CustomerRepo;
import lk.ijse.helloshoe.service.CustomerService;
import lk.ijse.helloshoe.service.EmailService;
import lk.ijse.helloshoe.util.GenerateID;
import lk.ijse.helloshoe.util.Mapping;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
//@Transactional
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepo customerRepo;
    private final Mapping mapping;
    private final GenerateID generateID;
    private final EmailService emailService;

    //    log.info("Attempting to authenticate user: {}", signIn.getEmail());
    @Override
    public boolean saveCustomer(CustomerDTO customerDTO) {
        try {
            customerDTO.setCId(generateID.generateUUID());
            customerRepo.save(mapping.toCustomer(customerDTO));
            log.info("Customer Saved");
            return true;

        } catch (Exception e) {
            log.error("Customer save failed");
            throw new DuplicateException("Customer Duplicate Details Entered");
        }

    }

    @Override
    public CustomerDTO getCustomer(String cId) {
        if (customerRepo.existsById(cId)) {
            log.info("Customer fetch");
            return mapping.toCustomerDTO(customerRepo.getReferenceById(cId));

        }

        log.error("Customer fetch abort");
        throw new NotFoundException("Customer Not Found");

    }

    @Override
    public boolean updateCustomer(CustomerDTO customerDTO) {
        if (customerRepo.existsById(customerDTO.getCId())) {
            try {
                customerRepo.save(mapping.toCustomer(customerDTO));
                log.info("customer updated");
                return true;

            } catch (Exception e) {
                log.error("customer update failed");
                throw new DuplicateException("Customer Duplicate DAta Entered");

            }

        }

        throw new NotFoundException("Customer Not Found");

    }

    @Override
    public boolean deleteCustomer(String cId) {
        if (customerRepo.existsById(cId)) {
            customerRepo.deleteById(cId);
            log.info("customer deleted");
            return true;

        }

        log.error("customer delete failed");
        throw new NotFoundException("Customer Not Found");

    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        log.info("customer fetching all");
        return mapping.toCustomerDTOList(customerRepo.findAll());

    }

    @Override
    @Scheduled(cron = "0 0 8 * * ?")
    public void sendBirthdayWishes() {
        int day = LocalDate.now().getDayOfMonth();
        int month = LocalDate.now().getMonthValue();
        List<Customer> byBirthdayMonthAndDay = customerRepo.findByBirthdayMonthAndDay(month, day);
        for (Customer customer : byBirthdayMonthAndDay) {
            String customerEmail = customer.getEmail();
            String subject = "\uD83C\uDF89 Happy Birthday from Hello Shoes! \uD83C\uDF89";
            String message = "Dear " + customer.getCName() + "s," +
                    "\n\nHappy Birthday!" +
                    "\nWe wish you All the Best!" +
                    "\nHello Shoes Pvt. Ltd";
            EmailDTO emailDTO = new EmailDTO(null, customerEmail, subject, message);

            // send emails to customers
            emailService.sendSimpleEmail(emailDTO);
        }

    }
}
