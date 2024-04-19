package lk.ijse.helloshoe.entity;

import jakarta.persistence.*;
import lk.ijse.helloshoe.entity.compositeId.StockId;
import lk.ijse.helloshoe.entity.enums.Colour;
import lk.ijse.helloshoe.entity.enums.Size;
import lk.ijse.helloshoe.entity.enums.StockStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@IdClass(StockId.class)
@Entity
public class Stock {
    @Id
    private Size size;
    private int qty;
    private int maxQty;

    @Enumerated(EnumType.STRING)
    @Id
    private Colour colour;

    @Enumerated(EnumType.STRING)
    private StockStatus status;

    @ManyToOne
    @Id
    private Item item;

    @ManyToOne
    private ItemImage itemImage;

}
