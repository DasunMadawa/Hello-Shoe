package lk.ijse.helloshoe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;
import lk.ijse.helloshoe.entity.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SaleDTO {
    @JsonProperty("oId")
    @Pattern(regexp = "^(O)\\d{5}$")
    private String oId;
    private int itemQty;
    private Date date;
    private PaymentMethod paymentMethod;
    private int points;
    private List<SaleCartDTO> saleCartDTOList;
    private String customerId;
    private String employeeId;
    private double totalPrice;


}
