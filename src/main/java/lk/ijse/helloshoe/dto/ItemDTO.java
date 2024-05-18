package lk.ijse.helloshoe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lk.ijse.helloshoe.entity.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemDTO {
    @JsonProperty("iCode")
    private String iCode;
    private String description;
    private Category category;
    private double priceBuy;
    private double priceSell;

    private SupplierDTO supplierDTO;
    private List<StockDTO> stockList;

    private List<ItemImageDTO> itemImageDTOList;

}
