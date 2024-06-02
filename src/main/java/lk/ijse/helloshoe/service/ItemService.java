package lk.ijse.helloshoe.service;

import lk.ijse.helloshoe.dto.EmployeeDTO;
import lk.ijse.helloshoe.dto.ItemDTO;
import lk.ijse.helloshoe.dto.SaleItemHolderDTO;

import java.util.List;

public interface ItemService {
    boolean saveItem(ItemDTO itemDTO);
    ItemDTO getItem(String itemCode);
    boolean updateItem(ItemDTO itemDTO);
    boolean updateItemStocks(ItemDTO itemDTO);
    boolean updateItemStocksAll(List<ItemDTO> itemDTOList);
    boolean deleteItem(String itemCode);
    List<ItemDTO> getAllItems();
    List<SaleItemHolderDTO> getAllItemsForSale();

}
