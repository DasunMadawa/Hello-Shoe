package lk.ijse.helloshoe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lk.ijse.helloshoe.entity.enums.Category;
import lk.ijse.helloshoe.entity.enums.Colour;
import lk.ijse.helloshoe.entity.enums.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SaleItemHolderDTO {
    @JsonProperty("iCode")
    private String iCode;
    private List<String> tags;
    private String description;
//    private List<Size> availableSizeList;
    private List<Colour> availableColourList;
    private List<SaleItemQtyHolderDTO> saleItemQtyHolderDTOList;
    private List<SaleItemImageHolderDTO> saleItemImageHolderDTOList;
    private double price;

    private String category;
    private String gender;
    private String occasion;
    private String verities;

}
