package lk.ijse.helloshoe.service.impl;

import lk.ijse.helloshoe.dto.CustomerDTO;
import lk.ijse.helloshoe.dto.EmployeeDTO;
import lk.ijse.helloshoe.entity.Employee;
import lk.ijse.helloshoe.exception.DuplicateException;
import lk.ijse.helloshoe.exception.NotFoundException;
import lk.ijse.helloshoe.repo.CustomerRepo;
import lk.ijse.helloshoe.repo.EmployeeRepo;
import lk.ijse.helloshoe.service.EmployeeService;
import lk.ijse.helloshoe.util.GenerateID;
import lk.ijse.helloshoe.util.Mapping;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
//@Transactional
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
    private final Mapping mapping;
    private final EmployeeRepo employeeRepo;
    private final GenerateID generateID;

    @Override
    public boolean saveEmployee(EmployeeDTO employeeDTO) {
        try {
            employeeDTO.setEmployeeId(generateID.generateUUID());
            employeeRepo.save(mapping.toEmployee(employeeDTO));
            log.info("Employee saved");
            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            log.error("Employee save failed");
            throw new DuplicateException("Employee Duplicate Details Entered");
        }

    }

    @Override
    public EmployeeDTO getEmployee(String employeeId) {
        if (employeeRepo.existsById(employeeId)) {
            log.info("Employee fetched");
            return mapping.toEmployeeDTO(employeeRepo.getReferenceById(employeeId));

        }

        log.error("Employee fetch failed");
        throw new NotFoundException("Employee Not Found");

    }

    @Override
    public boolean updateEmployee(EmployeeDTO employeeDTO) {
        if (employeeRepo.existsById(employeeDTO.getEmployeeId())) {
            try {
                employeeRepo.save(mapping.toEmployee(employeeDTO));
                log.info("Employee updated");
                return true;

            } catch (Exception e) {
                log.error("Employee not updated");
                throw new DuplicateException("Employee Duplicate Data Entered");

            }

        }

        log.error("Employee not updated (not found)");
        throw new NotFoundException("Employee Not Found");

    }

    @Override
    public boolean deleteEmployee(String employeeId) {
        if (employeeRepo.existsById(employeeId)) {
            employeeRepo.deleteById(employeeId);
        log.info("Employee deleted");
            return true;

        }

        log.error("Employee delete failed");
        throw new NotFoundException("Employee Not Found");

    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        log.info("All employee fetched");
        return mapping.toEmployeeDTOList(employeeRepo.findAll());

    }

    @Override
    public EmployeeDTO getEmployeeByEmail(String mail) {
//        return mapping.toEmployeeDTO(employeeRepo.findByEmail(mail).get());
        return null;
    }

}
