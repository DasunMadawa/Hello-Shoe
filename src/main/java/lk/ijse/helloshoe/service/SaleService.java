package lk.ijse.helloshoe.service;

import lk.ijse.helloshoe.dto.SaleDTO;
import lk.ijse.helloshoe.dto.SaleItemHolderDTO;

import java.util.List;

public interface SaleService {
    List<SaleDTO> getAllSales();
    boolean placeOrder(SaleDTO saleDTO);
    SaleDTO getSale(String saleId);

}
