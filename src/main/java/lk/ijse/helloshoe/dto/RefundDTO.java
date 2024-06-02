package lk.ijse.helloshoe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lk.ijse.helloshoe.entity.Employee;
import lk.ijse.helloshoe.entity.ItemSale;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RefundDTO {
    @JsonProperty("rId")
    private String rId;
    private double value;
    private Date date;
    private String reason;
    private int qty;

    private String employeeId;
    private String saleId;
    private String itemSaleId;

    private SaleCartDTO saleCartDTO;


}
