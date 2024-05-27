package lk.ijse.helloshoe.service;

import lk.ijse.helloshoe.dto.EmployeeDTO;
import lk.ijse.helloshoe.dto.ItemDTO;

import java.util.List;

public interface ItemService {
    boolean saveItem(ItemDTO itemDTO);
    ItemDTO getItem(String itemCode);
    boolean updateItem(ItemDTO itemDTO);
    boolean updateItemStocks(ItemDTO itemDTO);
    boolean deleteItem(String itemCode);
    List<ItemDTO> getAllItems();

}
