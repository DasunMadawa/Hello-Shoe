package lk.ijse.helloshoe.service.impl;

import lk.ijse.helloshoe.dto.RefundDTO;
import lk.ijse.helloshoe.dto.SaleCartDTO;
import lk.ijse.helloshoe.dto.SaleDTO;
import lk.ijse.helloshoe.entity.*;
import lk.ijse.helloshoe.entity.enums.StockStatus;
import lk.ijse.helloshoe.exception.InvalidateException;
import lk.ijse.helloshoe.exception.NotFoundException;
import lk.ijse.helloshoe.repo.EmployeeRepo;
import lk.ijse.helloshoe.repo.ItemSaleRepo;
import lk.ijse.helloshoe.repo.RefundRepo;
import lk.ijse.helloshoe.repo.StockRepo;
import lk.ijse.helloshoe.service.RefundService;
import lk.ijse.helloshoe.service.SaleService;
import lk.ijse.helloshoe.util.GenerateID;
import lk.ijse.helloshoe.util.Mapping;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefundServiceImpl implements RefundService {
    private final RefundRepo refundRepo;
    private final EmployeeRepo employeeRepo;
    private final ItemSaleRepo itemSaleRepo;
    private final StockRepo stockRepo;

    private final Mapping mapping;
    private final GenerateID generateID;

    private final SaleService saleService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveRefund(List<RefundDTO> refundDTOList) {
        try {
            for (RefundDTO refundDTO : refundDTOList) {
                Refund refund = mapping.toRefund(refundDTO);
                refund.setRId(generateID.generateUUID());

                Employee employee = employeeRepo.getReferenceById(refundDTO.getEmployeeId());
                ItemSale itemSale = itemSaleRepo.getReferenceById(refundDTO.getItemSaleId());

                Stock stock = stockRepo.getItemStock(itemSale.getColour().toString(), itemSale.getSize().toString(), itemSale.getItem().getICode());
                stock.setQty(stock.getQty() + refund.getQty());
                stock.setStatus(stock.getQty() == 0 ? StockStatus.LOW : (stock.getQty() * 2) > stock.getMaxQty() ? StockStatus.AVAILABLE : StockStatus.AVAILABLE);

                refund.setEmployee(employee);
                refund.setItemSale(itemSale);

                refundRepo.save(refund);

                log.info("item refunded");
            }

            return true;
        } catch (Exception e) {
            log.error("item fetched failed");
            e.printStackTrace();
            throw new InvalidateException("Refund data invalid");

        }


    }

    @Override
    public SaleDTO getSaleWithRefundChecks(String saleId) {
        try {
            SaleDTO saleDTO = saleService.getSale(saleId);
            List<SaleCartDTO> saleCartDTOList = new ArrayList<>();

            for (SaleCartDTO saleCartDTO : saleDTO.getSaleCartDTOList()) {
                String itemSaleId = saleCartDTO.getItemSaleId();

                System.out.println(itemSaleId);
                List<Refund> refundList = refundRepo.getRefundByItemSaleId(itemSaleId);

                if (refundList.size() > 0) {
                    int tempQty = 0;

                    for (Refund refund : refundList) {
                        tempQty += refund.getQty();

                    }

                    saleCartDTO.setQty(saleCartDTO.getQty() - tempQty);
                    saleCartDTO.setPriceTotal(saleCartDTO.getQty() * saleCartDTO.getPriceSingle());

                }

                if (saleCartDTO.getQty() > 0) {
                    saleCartDTOList.add(saleCartDTO);
                }

            }

            saleDTO.setSaleCartDTOList(saleCartDTOList);
            return saleDTO;

        } catch (Exception e) {
            throw new NotFoundException("Sale Not Found !");

        }

    }

    @Override
    public List<RefundDTO> getAllRefunds() {
        List<Refund> refundList = refundRepo.findAll();

        List<RefundDTO> refundDTOList = new ArrayList<>();

        for (Refund refund : refundList) {
            ItemSale itemSale = refund.getItemSale();
            Item item = itemSale.getItem();
            Stock stock = stockRepo.getItemStock(itemSale.getColour().toString(), itemSale.getSize().toString(), item.getICode());


            SaleCartDTO saleCartDTO = new SaleCartDTO(
                    item.getICode(),
                    item.getDescription(),
                    itemSale.getQty(),
                    itemSale.getSize(),
                    itemSale.getColour(),
                    item.getPriceSell(),
                    (item.getPriceSell() * itemSale.getQty()),
//                    stock.getItemImage().getImg(),
                    "",
                    itemSale.getItemSaleId()

            );

            refundDTOList.add(
                    new RefundDTO(
                            refund.getRId(),
                            refund.getValue(),
                            refund.getDate(),
                            refund.getReason(),
                            refund.getQty(),
                            refund.getEmployee().getEmployeeId(),
                            itemSale.getSale().getOId(),
                            itemSale.getItemSaleId(),
                            saleCartDTO

                    )
            );

        }

        return refundDTOList;

    }

    @Override
    public RefundDTO getRefund(String refundId) {
        try {
            Refund refund = refundRepo.getReferenceById(refundId);
            ItemSale itemSale = refund.getItemSale();
            Item item = itemSale.getItem();
            Stock stock = stockRepo.getItemStock(itemSale.getColour().toString(), itemSale.getSize().toString(), item.getICode());


            SaleCartDTO saleCartDTO = new SaleCartDTO(
                    item.getICode(),
                    item.getDescription(),
                    refund.getQty(),
                    itemSale.getSize(),
                    itemSale.getColour(),
                    item.getPriceSell(),
                    (item.getPriceSell() * refund.getQty()),
                    stock.getItemImage().getImg(),
                    itemSale.getItemSaleId()
            );

            RefundDTO refundDTO = new RefundDTO(
                    refund.getRId(),
                    refund.getValue(),
                    refund.getDate(),
                    refund.getReason(),
                    refund.getQty(),
                    refund.getEmployee().getEmployeeId(),
                    itemSale.getSale().getOId(),
                    itemSale.getItemSaleId(),
                    saleCartDTO

            );
            return refundDTO;
        } catch (Exception e) {
            throw new NotFoundException("Refund Not Found !");

        }
    }

    @Override
    public List<RefundDTO> getRefundOnSaleId(String saleId) {
        try {
            List<RefundDTO> refundDTOList = new ArrayList<>();

            List<Refund> refundList = refundRepo.getRefundsBySaleId(saleId);

            for (int i = 0; i < refundList.size(); i++) {
                RefundDTO refundDTO = getRefund(refundList.get(i).getRId());
                refundDTOList.add(refundDTO);

            }

            return refundDTOList;
        } catch (Exception e) {
            throw new NotFoundException("Refund Not Found !");
        }

    }


}
