package lk.ijse.helloshoe.service;

import lk.ijse.helloshoe.dto.EmployeeDTO;
import lk.ijse.helloshoe.dto.SupplierDTO;

import java.util.List;

public interface SupplierService {
    boolean saveSupplier(SupplierDTO supplierDTO);
    SupplierDTO getSupplier(String supplierId);
    boolean updateSupplier(SupplierDTO supplierDTO);
    boolean deleteSupplier(String supplierId);
    List<SupplierDTO> getAllSuppliers();

}
