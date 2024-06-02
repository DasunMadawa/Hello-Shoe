package lk.ijse.helloshoe.api;

import lk.ijse.helloshoe.dto.ItemDTO;
import lk.ijse.helloshoe.dto.SaleItemHolderDTO;
import lk.ijse.helloshoe.exception.DuplicateException;
import lk.ijse.helloshoe.exception.NotFoundException;
import lk.ijse.helloshoe.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/v1/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @GetMapping("/health")
    public String itemHealthCheck(){
        return "Item OK";

    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemDTO> saveItem(@RequestBody ItemDTO itemDTO){
        try {
            itemService.saveItem(itemDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(itemDTO);

        } catch (DuplicateException duplicateException){
            System.out.println(duplicateException.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        }

    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ItemDTO>> getAllItems() {
        return ResponseEntity.ok(itemService.getAllItems());

    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/getAll" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SaleItemHolderDTO>> getAllItemsWithStockImages() {
        return ResponseEntity.ok(itemService.getAllItemsForSale());

    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemDTO> getItem(@PathVariable("id") String itemCode) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(itemService.getItem(itemCode));

        } catch (NotFoundException notFoundException) {
            System.out.println(notFoundException.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        }

    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping(value = "{id}" , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemDTO> updateItem(@PathVariable("id") String itemCode , @RequestBody ItemDTO itemDTO) {
        itemDTO.setICode(itemCode);
        try {
            itemService.updateItem(itemDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(itemDTO);
        } catch (NotFoundException | DuplicateException exception) {
            System.out.println(exception.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        }

    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping(value = "/qty/{id}" , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemDTO> updateItemStocks(@PathVariable("id") String itemCode , @RequestBody ItemDTO itemDTO) {
        itemDTO.setICode(itemCode);
        try {
            itemService.updateItemStocks(itemDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(itemDTO);
        } catch (NotFoundException | DuplicateException exception) {
            System.out.println(exception.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        }

    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping(value = "/qty" , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ItemDTO>> updateItemStocksAll(@RequestBody List<ItemDTO> itemDTOList) {
        try {
            itemService.updateItemStocksAll(itemDTOList);
            return ResponseEntity.status(HttpStatus.CREATED).body(itemDTOList);
        } catch (NotFoundException | DuplicateException exception) {
            System.out.println(exception.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        }

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ItemDTO> deleteItem(@PathVariable("id") String itemCode) {
        try {
            itemService.deleteItem(itemCode);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (NotFoundException notFoundException) {
            System.out.println(notFoundException.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }

}
