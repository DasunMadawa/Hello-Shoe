package lk.ijse.helloshoe.api;

import lk.ijse.helloshoe.dto.RefundDTO;
import lk.ijse.helloshoe.exception.InvalidateException;
import lk.ijse.helloshoe.service.RefundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/refund")
public class RefundController {
    @Autowired
    private RefundService refundService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RefundDTO>> saveRefund(@RequestBody List<RefundDTO> refundDTOList) {
        try {
            refundService.saveRefund(refundDTOList);
            return ResponseEntity.status(HttpStatus.CREATED).body(refundDTOList);
        } catch (InvalidateException invalidateException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        }

    }



}
