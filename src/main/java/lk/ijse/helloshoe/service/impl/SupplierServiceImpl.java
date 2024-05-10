package lk.ijse.helloshoe.service.impl;

import lk.ijse.helloshoe.dto.SupplierDTO;
import lk.ijse.helloshoe.exception.DuplicateException;
import lk.ijse.helloshoe.exception.NotFoundException;
import lk.ijse.helloshoe.repo.SupplierRepo;
import lk.ijse.helloshoe.service.SupplierService;
import lk.ijse.helloshoe.util.GenerateID;
import lk.ijse.helloshoe.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
//@Transactional
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepo supplierRepo;
    private final GenerateID generateID;
    private final Mapping mapping;

    @Override
    public boolean saveSupplier(SupplierDTO supplierDTO) {
        try {
            supplierDTO.setSupplierId(generateID.generateUUID());
            supplierRepo.save(mapping.toSupplier(supplierDTO));
            return true;

        } catch (Exception e) {
            throw new DuplicateException("Supplier Duplicate Details Entered");
        }

    }

    @Override
    public SupplierDTO getSupplier(String supplierId) {
        if (supplierRepo.existsById(supplierId)) {
            return mapping.toSupplierDTO(supplierRepo.getReferenceById(supplierId));

        }

        throw new NotFoundException("Supplier Not Found");

    }

    @Override
    public boolean updateSupplier(SupplierDTO supplierDTO) {
        if (supplierRepo.existsById(supplierDTO.getSupplierId())) {
            try {
                System.out.println(supplierDTO);
                supplierRepo.save(mapping.toSupplier(supplierDTO));

            } catch (Exception e) {
                throw new DuplicateException("Supplier Duplicate Data Entered");

            }
            return true;

        }

        throw new NotFoundException("Supplier Not Found");

    }

    @Override
    public boolean deleteSupplier(String supplierId) {
        if (supplierRepo.existsById(supplierId)) {
            supplierRepo.deleteById(supplierId);
            return true;

        }

        throw new NotFoundException("Supplier Not Found");

    }

    @Override
    public List<SupplierDTO> getAllSuppliers() {
        return mapping.toSupplierDTOList(supplierRepo.findAll());

    }

}
