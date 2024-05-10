package lk.ijse.helloshoe.service;

import lk.ijse.helloshoe.dto.CustomerDTO;
import lk.ijse.helloshoe.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeService {
    boolean saveEmployee(EmployeeDTO employeeDTO);
    EmployeeDTO getEmployee(String employeeId);
    boolean updateEmployee(EmployeeDTO employeeDTO);
    boolean deleteEmployee(String employeeId);
    List<EmployeeDTO> getAllEmployees();

}
