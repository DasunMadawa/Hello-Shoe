package lk.ijse.helloshoe.dto;

import lk.ijse.helloshoe.entity.enums.Colour;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SaleItemImageHolderDTO {
    private Colour colour;
    private String image;

}
