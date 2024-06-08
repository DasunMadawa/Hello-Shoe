package lk.ijse.helloshoe.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lk.ijse.helloshoe.entity.enums.Colour;
import lk.ijse.helloshoe.entity.enums.Size;
import lk.ijse.helloshoe.entity.enums.StockStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StockDTO {
    @jakarta.validation.constraints.Size(min = 0)
    private Size size;
    @jakarta.validation.constraints.Size(min = 0)
    private int qty;
    @jakarta.validation.constraints.Size(min = 0)
    private int maxQty;

    private Colour colour;

    private StockStatus status;
    private String itemImgId;

}
