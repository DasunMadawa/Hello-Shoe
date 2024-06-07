package lk.ijse.helloshoe.entity.compositeId;

import lk.ijse.helloshoe.entity.Item;
import lk.ijse.helloshoe.entity.Resupply;
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
public class ItemResupplyId implements Serializable {
    private Item item;
    private Resupply resupply;
    private Colour colour;
    private Size size;

}
