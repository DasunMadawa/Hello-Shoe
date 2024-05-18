package lk.ijse.helloshoe.service.impl;

import lk.ijse.helloshoe.dto.ItemDTO;
import lk.ijse.helloshoe.entity.Item;
import lk.ijse.helloshoe.entity.Stock;
import lk.ijse.helloshoe.entity.Supplier;
import lk.ijse.helloshoe.exception.DuplicateException;
import lk.ijse.helloshoe.exception.NotFoundException;
import lk.ijse.helloshoe.repo.ItemImageRepo;
import lk.ijse.helloshoe.repo.ItemRepo;
import lk.ijse.helloshoe.repo.SupplierRepo;
import lk.ijse.helloshoe.service.ItemService;
import lk.ijse.helloshoe.util.GenerateID;
import lk.ijse.helloshoe.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public boolean updateItem(ItemDTO itemDTO) {
        return false;
    }

    @Override
    public boolean deleteItem(String itemCode) {
        return false;
    }

    @Override
    public List<ItemDTO> getAllItems() {
        return mapping.toItemDTOList(itemRepo.findAll());

    }
}