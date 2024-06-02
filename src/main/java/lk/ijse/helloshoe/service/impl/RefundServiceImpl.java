package lk.ijse.helloshoe.service.impl;

import lk.ijse.helloshoe.dto.RefundDTO;
import lk.ijse.helloshoe.entity.Employee;
import lk.ijse.helloshoe.entity.ItemSale;
import lk.ijse.helloshoe.entity.Refund;
import lk.ijse.helloshoe.exception.InvalidateException;
import lk.ijse.helloshoe.repo.EmployeeRepo;
import lk.ijse.helloshoe.repo.ItemSaleRepo;
import lk.ijse.helloshoe.repo.RefundRepo;
import lk.ijse.helloshoe.service.RefundService;
import lk.ijse.helloshoe.util.GenerateID;
import lk.ijse.helloshoe.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RefundServiceImpl implements RefundService {
    private final RefundRepo refundRepo;
    private final EmployeeRepo employeeRepo;
    private final ItemSaleRepo itemSaleRepo;
    private final Mapping mapping;
    private final GenerateID generateID;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveRefund(List<RefundDTO> refundDTOList) {
        try {
            for (RefundDTO refundDTO:refundDTOList) {
                Refund refund = mapping.toRefund(refundDTO);
                refund.setRId(generateID.generateUUID());

                Employee employee = employeeRepo.getReferenceById(refundDTO.getEmployeeId());
                ItemSale itemSale = itemSaleRepo.getReferenceById(refundDTO.getItemSaleId());

                refund.setEmployee(employee);
                refund.setItemSale(itemSale);

                refundRepo.save(refund);

            }

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new InvalidateException("Refund data invalid");

        }


    }

}
