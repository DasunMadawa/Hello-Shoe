package lk.ijse.helloshoe.service.impl;

import lk.ijse.helloshoe.dto.SupplierDTO;
import lk.ijse.helloshoe.exception.DuplicateException;
import lk.ijse.helloshoe.exception.NotFoundException;
import lk.ijse.helloshoe.repo.SupplierRepo;
import lk.ijse.helloshoe.service.SupplierService;
import lk.ijse.helloshoe.util.GenerateID;
import lk.ijse.helloshoe.util.Mapping;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
//@Transactional
@Slf4j
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepo supplierRepo;
    private final GenerateID generateID;
    private final Mapping mapping;

    @Override
    public boolean saveSupplier(SupplierDTO supplierDTO) {
        try {
            supplierDTO.setSupplierId(generateID.generateUUID());
            supplierRepo.save(mapping.toSupplier(supplierDTO));
            log.info("supplier saved");
            return true;

        } catch (Exception e) {
            log.error("supplier saved failed");
            throw new DuplicateException("Supplier Duplicate Details Entered");
        }

    }

    @Override
    public SupplierDTO getSupplier(String supplierId) {
        if (supplierRepo.existsById(supplierId)) {
            log.info("supplier fetched");
            return mapping.toSupplierDTO(supplierRepo.getReferenceById(supplierId));

        }

        log.error("supplier fetched failed");

        throw new NotFoundException("Supplier Not Found");

    }

    @Override
    public boolean updateSupplier(SupplierDTO supplierDTO) {
        if (supplierRepo.existsById(supplierDTO.getSupplierId())) {
            try {
                System.out.println(supplierDTO);
                supplierRepo.save(mapping.toSupplier(supplierDTO));
                log.info("supplier updated successfully");

            } catch (Exception e) {
                log.error("supplier updated failed");
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
            log.info("supplier delete successfully");
            return true;

        }

        log.error("supplier delete failed");
        throw new NotFoundException("Supplier Not Found");

    }

    @Override
    public List<SupplierDTO> getAllSuppliers() {
        log.info("All suppliers fetched");
        return mapping.toSupplierDTOList(supplierRepo.findAll());

    }

}
