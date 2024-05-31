package lk.ijse.helloshoe.service.impl;

import lk.ijse.helloshoe.dto.SaleCartDTO;
import lk.ijse.helloshoe.dto.SaleDTO;
import lk.ijse.helloshoe.entity.Item;
import lk.ijse.helloshoe.entity.ItemSale;
import lk.ijse.helloshoe.entity.Sale;
import lk.ijse.helloshoe.entity.Stock;
import lk.ijse.helloshoe.entity.enums.StockStatus;
import lk.ijse.helloshoe.exception.DuplicateException;
import lk.ijse.helloshoe.repo.*;
import lk.ijse.helloshoe.service.SaleService;
import lk.ijse.helloshoe.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {
    private final SaleRepo saleRepo;
    private final Mapping mapping;
    private final ItemRepo itemRepo;
    private final CustomerRepo customerRepo;
    private final EmployeeRepo employeeRepo;
    private final StockRepo stockRepo;

    @Override
    public List<SaleDTO> getAllSales() {
        return mapping.toSaleDTOList(saleRepo.findAll());

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean placeOrder(SaleDTO saleDTO) {
        try {
            System.out.println(saleRepo.existsById(saleDTO.getOId()));
            if (saleRepo.existsById(saleDTO.getOId())) {
                throw new DuplicateException("Sale is already saved");
            }

            Sale sale = mapping.toSale(saleDTO);

            List<ItemSale> itemSaleList = new ArrayList<>();

            for (SaleCartDTO saleCartDTO : saleDTO.getSaleCartDTOList()) {
                Item item = itemRepo.getReferenceById(saleCartDTO.getICode());

                String tempItemSaleId =
                        saleCartDTO.getICode() + "_" +
                                saleDTO.getOId() + "_" +
                                saleCartDTO.getColour() + "_" +
                                saleCartDTO.getSize();

                itemSaleList.add(new ItemSale(
                        tempItemSaleId,
                        sale,
                        item,
                        null,
                        saleCartDTO.getSize(),
                        saleCartDTO.getColour(),
                        saleCartDTO.getQty()

                ));

                Stock stock = stockRepo.getItemStock(saleCartDTO.getColour().toString(), saleCartDTO.getSize().toString(), saleCartDTO.getICode());
                stock.setQty(stock.getQty() - saleCartDTO.getQty());
                stock.setStatus(
                        stock.getMaxQty() < (2 * stock.getQty()) ?
                                StockStatus.AVAILABLE : stock.getQty() == 0 ?
                                StockStatus.NOT_AVAILABLE : StockStatus.LOW
                );

            }

            sale.setItemSaleList(itemSaleList);
            sale.setCustomer(customerRepo.getReferenceById(saleDTO.getCustomerId()));
            sale.setEmployee(employeeRepo.getReferenceById(saleDTO.getEmployeeId()));

            saleRepo.save(sale);
            return true;

//        itemService.getItem()

        } catch (Exception e) {


        }

        return false;

    }


}