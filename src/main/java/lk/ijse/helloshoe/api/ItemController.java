package lk.ijse.helloshoe.api;

import lk.ijse.helloshoe.dto.EmployeeDTO;
import lk.ijse.helloshoe.dto.ItemDTO;
import lk.ijse.helloshoe.exception.DuplicateException;
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

}
