package lk.ijse.helloshoe.entity.compositeId;

import lk.ijse.helloshoe.entity.Item;
import lk.ijse.helloshoe.entity.enums.Colour;
import lk.ijse.helloshoe.entity.enums.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class StockId implements Serializable {
    private Size size;
    private Colour colour;
    private Item item;

}
