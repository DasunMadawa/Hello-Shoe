package lk.ijse.helloshoe.api;

import jakarta.annotation.security.RolesAllowed;
import lk.ijse.helloshoe.dto.SupplierDTO;
import lk.ijse.helloshoe.entity.Supplier;
import lk.ijse.helloshoe.exception.DuplicateException;
import lk.ijse.helloshoe.exception.NotFoundException;
import lk.ijse.helloshoe.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/supplier")
@CrossOrigin
public class SupplierController {
    @Autowired
    private SupplierService supplierService;

    @GetMapping("/health")
    public String supplierHealthCheck() {
        return "Supplier OK";

    }

    @RolesAllowed("ADMIN")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SupplierDTO> saveSupplier(@RequestBody SupplierDTO supplierDTO) {
        try {
            supplierService.saveSupplier(supplierDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(supplierDTO);
        } catch (DuplicateException duplicateException) {
            System.out.println(duplicateException.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        }

    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "{id}")
    public ResponseEntity<SupplierDTO> getSupplier(@PathVariable("id") String supplierId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(supplierService.getSupplier(supplierId));
        } catch (NotFoundException notFoundException) {
            System.out.println(notFoundException.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        }

    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ResponseEntity<List<SupplierDTO>> getAllSupplierDTO() {
        return ResponseEntity.ok(supplierService.getAllSuppliers());

    }

    @RolesAllowed("ADMIN")
    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SupplierDTO> updateSupplier(@PathVariable("id") String supplierId , @RequestBody SupplierDTO supplierDTO) {
        supplierDTO.setSupplierId(supplierId);
        try {
            supplierService.updateSupplier(supplierDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(supplierDTO);
        } catch (NotFoundException | DuplicateException exception) {
            System.out.println(exception.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        }

    }

    @RolesAllowed("ADMIN")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public ResponseEntity<SupplierDTO> deleteSupplier(@PathVariable("id") String supplierId) {
        try {
            supplierService.deleteSupplier(supplierId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (NotFoundException notFoundException) {
            System.out.println(notFoundException.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        }

    }


}
