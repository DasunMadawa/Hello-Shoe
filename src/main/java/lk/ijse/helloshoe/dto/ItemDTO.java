package lk.ijse.helloshoe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private String description;
    private Category category;
    private double priceBuy;
    private double priceSell;
    @NotNull
    private SupplierDTO supplierDTO;
    private List<StockDTO> stockList;

    private List<ItemImageDTO> itemImageDTOList;

}
