package lk.ijse.helloshoe.service.impl;

import lk.ijse.helloshoe.dto.CustomerDTO;
import lk.ijse.helloshoe.dto.EmployeeDTO;
import lk.ijse.helloshoe.exception.DuplicateException;
import lk.ijse.helloshoe.exception.NotFoundException;
import lk.ijse.helloshoe.repo.CustomerRepo;
import lk.ijse.helloshoe.repo.EmployeeRepo;
import lk.ijse.helloshoe.service.EmployeeService;
import lk.ijse.helloshoe.util.GenerateID;
import lk.ijse.helloshoe.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
//@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    private final Mapping mapping;
    private final EmployeeRepo employeeRepo;
    private final GenerateID generateID;

    @Override
    public boolean saveEmployee(EmployeeDTO employeeDTO) {
        try {
            employeeDTO.setEmployeeId(generateID.generateUUID());
            employeeRepo.save(mapping.toEmployee(employeeDTO));

            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new DuplicateException("Employee Duplicate Details Entered");
        }

    }

    @Override
    public EmployeeDTO getEmployee(String employeeId) {
        if (employeeRepo.existsById(employeeId)) {
            return mapping.toEmployeeDTO(employeeRepo.getReferenceById(employeeId));

        }

        throw new NotFoundException("Employee Not Found");

    }

    @Override
    public boolean updateEmployee(EmployeeDTO employeeDTO) {
        if (employeeRepo.existsById(employeeDTO.getEmployeeId())) {
            try {
                employeeRepo.save(mapping.toEmployee(employeeDTO));
                return true;

            } catch (Exception e) {
                throw new DuplicateException("Employee Duplicate Data Entered");

            }

        }

        throw new NotFoundException("Employee Not Found");

    }

    @Override
    public boolean deleteEmployee(String employeeId) {
        if (employeeRepo.existsById(employeeId)) {
            employeeRepo.deleteById(employeeId);
            return true;

        }

        throw new NotFoundException("Employee Not Found");

    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return mapping.toEmployeeDTOList(employeeRepo.findAll());

    }

}
