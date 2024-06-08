package lk.ijse.helloshoe.api;

import jakarta.annotation.security.RolesAllowed;
import lk.ijse.helloshoe.dto.RefundDTO;
import lk.ijse.helloshoe.dto.SaleDTO;
import lk.ijse.helloshoe.exception.InvalidateException;
import lk.ijse.helloshoe.exception.NotFoundException;
import lk.ijse.helloshoe.service.RefundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/refund")
@CrossOrigin
public class RefundController {
    @Autowired
    private RefundService refundService;

    @RolesAllowed("ADMIN")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RefundDTO>> saveRefund(@RequestBody List<RefundDTO> refundDTOList) {
        try {
            refundService.saveRefund(refundDTOList);
            return ResponseEntity.status(HttpStatus.CREATED).body(refundDTOList);
        } catch (InvalidateException invalidateException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        }

    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SaleDTO> getSale(@PathVariable("id") String saleId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(refundService.getSaleWithRefundChecks(saleId));

        } catch (NotFoundException notFoundException) {
            System.out.println(notFoundException.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        }

    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RefundDTO>> getAllRefunds() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(refundService.getAllRefunds());

        } catch (NotFoundException notFoundException) {
            System.out.println(notFoundException.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/rid/{rid}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RefundDTO> getRefund(@PathVariable ("rid") String refundId){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(refundService.getRefund(refundId));
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


}
