package lk.ijse.helloshoe.dto;

import lk.ijse.helloshoe.entity.enums.Colour;
import lk.ijse.helloshoe.entity.enums.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SaleItemQtyHolderDTO {
    private Size size;
    private Colour colour;
    private int qty;


}
