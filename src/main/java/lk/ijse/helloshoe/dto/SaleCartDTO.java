package lk.ijse.helloshoe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lk.ijse.helloshoe.entity.enums.Colour;
import lk.ijse.helloshoe.entity.enums.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SaleCartDTO {
    @JsonProperty("iCode")
    private String iCode;
    private String description;
    private int qty;
    private Size size;
    private Colour colour;
    private double priceSingle;
    private double priceTotal;
    private String image;

}
