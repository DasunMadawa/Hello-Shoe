package lk.ijse.helloshoe.api;

import lk.ijse.helloshoe.dto.ItemDTO;
import lk.ijse.helloshoe.dto.RefundDTO;
import lk.ijse.helloshoe.dto.SaleCartDTO;
import lk.ijse.helloshoe.dto.SaleDTO;
import lk.ijse.helloshoe.exception.DuplicateException;
import lk.ijse.helloshoe.exception.NotFoundException;
import lk.ijse.helloshoe.service.SaleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/sale")
@CrossOrigin
//@Slf4j
public class SaleController {
    @Autowired
    private SaleService saleService;

    @GetMapping("/health")
    public String OrderHealthCheck() {
        return "Order OK!";

    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SaleDTO>> getAllOrders() {
        return ResponseEntity.status(HttpStatus.OK).body(saleService.getAllSales());

    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SaleDTO> placeOrder(@RequestBody SaleDTO saleDTO) {
        ResponseEntity.status(HttpStatus.OK).body(saleDTO);

        try {
            boolean isSaved = saleService.placeOrder(saleDTO);
            if (isSaved) {
                return ResponseEntity.status(HttpStatus.CREATED).body(saleDTO);

            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(saleDTO);

            }

        } catch (DuplicateException duplicateException) {
            System.out.println(duplicateException.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        }

    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SaleDTO> getSale(@PathVariable("id") String saleId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(saleService.getSale(saleId));

        } catch (NotFoundException notFoundException) {
            System.out.println(notFoundException.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        }

    }


}
