package lk.ijse.helloshoe.service.impl;

import lk.ijse.helloshoe.dto.ItemDTO;
import lk.ijse.helloshoe.dto.SaleItemHolderDTO;
import lk.ijse.helloshoe.dto.SaleItemQtyHolderDTO;
import lk.ijse.helloshoe.dto.SupplierDTO;
import lk.ijse.helloshoe.entity.Item;
import lk.ijse.helloshoe.entity.Stock;
import lk.ijse.helloshoe.entity.Supplier;
import lk.ijse.helloshoe.exception.DuplicateException;
import lk.ijse.helloshoe.exception.InvalidateException;
import lk.ijse.helloshoe.exception.NotFoundException;
import lk.ijse.helloshoe.repo.ItemImageRepo;
import lk.ijse.helloshoe.repo.ItemRepo;
import lk.ijse.helloshoe.repo.SupplierRepo;
import lk.ijse.helloshoe.service.ItemService;
import lk.ijse.helloshoe.util.GenerateID;
import lk.ijse.helloshoe.util.Mapping;
import lk.ijse.helloshoe.util.MultipartFileToStringEditor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
//@Transactional
public class ItemServiceImpl implements ItemService {
    private final ItemRepo itemRepo;
    private final SupplierRepo supplierRepo;
    private final ItemImageRepo itemImageRepo;
    private final Mapping mapping;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveItem(ItemDTO itemDTO) {
        try {
            if (itemRepo.existsById(itemDTO.getICode())) {
                throw new DuplicateException("Already saved item");

            }

            Item item = mapping.toItem(itemDTO);
            Supplier supplier = supplierRepo.getReferenceById(item.getSupplier().getSupplierId());

            item.setSupplier(supplier);

            for (Stock stock : item.getStockList()) {
                if (itemRepo.existsById(stock.getItemImage().getImgId())) {
                    continue;
                }
                itemImageRepo.save(stock.getItemImage());
            }

            itemRepo.save(item);
            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new DuplicateException("Item Duplicate Details Entered");

        }

    }

    @Override
    public ItemDTO getItem(String itemCode) {
        if (itemRepo.existsById(itemCode)) {
            return mapping.toItemDTO(itemRepo.getReferenceById(itemCode));

        }

        throw new NotFoundException("Item Not Found");

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateItem(ItemDTO itemDTO) {
        try {
            if (itemRepo.existsById(itemDTO.getICode())) {
                Item itemFetched = itemRepo.getReferenceById(itemDTO.getICode());
                itemFetched.setDescription(itemDTO.getDescription());
                itemFetched.setPriceBuy(itemDTO.getPriceBuy());
                itemFetched.setPriceSell(itemDTO.getPriceSell());

                List<Stock> stockList = mapping.getStockList(itemDTO, itemFetched);

                for (Stock stock : stockList) {
                    itemFetched.getStockList().add(stock);
                    itemImageRepo.save(stock.getItemImage());
                }

                itemRepo.save(itemFetched);
                return true;

            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DuplicateException("Item Duplicate Data Entered");

        }


        throw new NotFoundException("Item Not Found");

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateItemStocks(ItemDTO itemDTO) {
        try {
            if (itemRepo.existsById(itemDTO.getICode())) {
                Item itemFetched = itemRepo.getReferenceById(itemDTO.getICode());

                itemFetched.setStockList(mapping.toStockList(itemDTO.getStockList(), itemFetched));

                itemRepo.save(itemFetched);
                return true;

            }
        } catch (Exception e) {
//            e.printStackTrace();
            throw new DuplicateException("Item Duplicate Data Entered");

        }


        throw new NotFoundException("Item Not Found");

    }

    @Transactional
    @Override
    public boolean updateItemStocksAll(List<ItemDTO> itemDTOList) {
        try {

            for (ItemDTO itemDTO : itemDTOList) {
                boolean isUpdated = updateItemStocks(itemDTO);

                if (!isUpdated) {
                    return false;
                }

            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidateException("Item Data Invalid");

        }

    }

    @Override
    public boolean deleteItem(String itemCode) {
        if (itemRepo.existsById(itemCode)) {
            itemRepo.deleteById(itemCode);
            return true;

        }

        throw new NotFoundException("Item Not Found");

    }

    @Override
    public List<ItemDTO> getAllItems() {
        return mapping.toItemDTOList(itemRepo.findAll());

    }

    @Override
    public List<SaleItemHolderDTO> getAllItemsForSale() {
        return mapping.getSaleItems(itemRepo.findAll());

    }

}
