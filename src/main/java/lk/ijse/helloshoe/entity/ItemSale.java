package lk.ijse.helloshoe.entity;

import jakarta.persistence.*;
import lk.ijse.helloshoe.entity.compositeId.ItemSaleId;
import lk.ijse.helloshoe.entity.enums.Colour;
import lk.ijse.helloshoe.entity.enums.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "item_sale")
@IdClass(ItemSaleId.class)
@Entity
public class ItemSale {
    @ManyToOne
    @Id
    private Sale sale;
    @ManyToOne
    @Id
    private Item item;

    private int qty;

    @Enumerated(EnumType.STRING)
    @Id
    private Size size;

    @Enumerated(EnumType.STRING)
    @Id
    private Colour colour;

    @OneToOne(mappedBy = "itemSale")
    private Refund refund;

}
