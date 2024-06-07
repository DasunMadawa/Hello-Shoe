package lk.ijse.helloshoe.service;

import lk.ijse.helloshoe.dto.RefundDTO;
import lk.ijse.helloshoe.dto.SaleDTO;

import java.util.List;

public interface RefundService {
    boolean saveRefund(List<RefundDTO> refundDTOList);
    SaleDTO getSaleWithRefundChecks(String saleId);
    List<RefundDTO> getAllRefunds();

    RefundDTO getRefund(String refundId);
    List<RefundDTO> getRefundOnSaleId(String refundId);

}
