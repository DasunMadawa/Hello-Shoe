package lk.ijse.helloshoe.service.impl;

import lk.ijse.helloshoe.dto.SaleCartDTO;
import lk.ijse.helloshoe.dto.SaleDTO;
import lk.ijse.helloshoe.entity.Item;
import lk.ijse.helloshoe.entity.ItemSale;
import lk.ijse.helloshoe.entity.Sale;
import lk.ijse.helloshoe.entity.Stock;
import lk.ijse.helloshoe.entity.enums.StockStatus;
import lk.ijse.helloshoe.exception.DuplicateException;
import lk.ijse.helloshoe.exception.InvalidateException;
import lk.ijse.helloshoe.exception.NotFoundException;
import lk.ijse.helloshoe.repo.*;
import lk.ijse.helloshoe.service.SaleService;
import lk.ijse.helloshoe.util.Mapping;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SaleServiceImpl implements SaleService {
    private final SaleRepo saleRepo;
    private final Mapping mapping;
    private final ItemRepo itemRepo;
    private final CustomerRepo customerRepo;
    private final EmployeeRepo employeeRepo;
    private final StockRepo stockRepo;

    @Override
    public List<SaleDTO> getAllSales() {
        log.info("All items fetched");
        return mapping.toSaleDTOList(saleRepo.findAll());

    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public boolean placeOrder(SaleDTO saleDTO) {
        try {
            System.out.println(saleRepo.existsById(saleDTO.getOId()));
            if (saleRepo.existsById(saleDTO.getOId())) {
                throw new DuplicateException("Sale is already saved");
            }

            Sale sale = mapping.toSale(saleDTO);
            sale.setOId(saleDTO.getOId());

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
            if (saleDTO.getCustomerId() != null && saleDTO.getCustomerId().length() > 0) {
                sale.setCustomer(customerRepo.getReferenceById(saleDTO.getCustomerId()));

            }
            sale.setItemSaleList(itemSaleList);
            sale.setEmployee(employeeRepo.getReferenceById(saleDTO.getEmployeeId()));

            saleRepo.save(sale);
            log.info("sale placed");
            return true;

//        itemService.getItem()

        } catch (NotFoundException e) {
            log.error("sale place failed");


        }

        return false;

    }

    @Override
    public SaleDTO getSale(String saleId) {
        try {

            Sale sale = saleRepo.getReferenceById(saleId);
            SaleDTO saleDTO = mapping.toSaleDTO(sale);

            if (sale.getCustomer() != null) {
                saleDTO.setCustomerId(sale.getCustomer().getCId());

            }

            List<SaleCartDTO> saleCartDTOList = new ArrayList<>();

            for (ItemSale itemSale : sale.getItemSaleList()) {
                Item item = itemRepo.getReferenceById(itemSale.getItem().getICode());
                Stock stock = stockRepo.getItemStock(itemSale.getColour().toString(), itemSale.getSize().toString(), itemSale.getItem().getICode());


                saleCartDTOList.add(
                        new SaleCartDTO(
                                itemSale.getItem().getICode(),
                                item.getDescription(),
                                itemSale.getQty(),
                                itemSale.getSize(),
                                itemSale.getColour(),
                                item.getPriceSell(),
                                (item.getPriceSell() * itemSale.getQty()),
                                stock.getItemImage().getImg(),
                                itemSale.getItemSaleId()

                        )
                );

            }

            saleDTO.setSaleCartDTOList(saleCartDTOList);
            log.info("sale details fetched");
            return saleDTO;

        } catch (Exception e) {
            log.error("sale details fetching failed");
            throw new NotFoundException("Sale Not Found !");

        }

    }


}
