package lk.ijse.helloshoe.api;

import lk.ijse.helloshoe.dto.EmployeeDTO;
import lk.ijse.helloshoe.exception.DuplicateException;
import lk.ijse.helloshoe.exception.NotFoundException;
import lk.ijse.helloshoe.service.EmployeeService;
import lk.ijse.helloshoe.util.MultipartFileToStringEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/v1/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, "profilePic", new MultipartFileToStringEditor());
    }


    @GetMapping("/health")
    public String employeeHealthCheck() {
        return "Employee OK";

    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EmployeeDTO> saveEmployee(@ModelAttribute EmployeeDTO employeeDTO){
        try {
            employeeService.saveEmployee(employeeDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(employeeDTO);

        } catch (DuplicateException duplicateException){
            System.out.println(duplicateException.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        }

    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());

    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable("id") String employeeId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(employeeService.getEmployee(employeeId));

        } catch (NotFoundException notFoundException) {
            System.out.println(notFoundException.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        }

    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping(value = "{id}" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable("id") String employeeId , @ModelAttribute EmployeeDTO employeeDTO) {
        employeeDTO.setEmployeeId(employeeId);
        try {
            employeeService.updateEmployee(employeeDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(employeeDTO);
        } catch (NotFoundException | DuplicateException exception) {
            System.out.println(exception.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        }

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "{id}")
    public ResponseEntity<EmployeeDTO> deleteEmployee(@PathVariable("id") String employeeId) {
        try {
            employeeService.deleteEmployee(employeeId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (NotFoundException notFoundException) {
            System.out.println(notFoundException.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        }

    }



}
