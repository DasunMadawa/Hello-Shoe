package lk.ijse.helloshoe.entity;

import jakarta.persistence.*;
import lk.ijse.helloshoe.entity.enums.Colour;
import lk.ijse.helloshoe.entity.enums.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "item_sale")
@Entity
public class ItemSale {
    @Id
    private String itemSaleId;

    @ManyToOne
    private Sale sale;
    @ManyToOne
    private Item item;
    @OneToOne(mappedBy = "itemSale")
    private Refund refund;


    @Enumerated(EnumType.STRING)
    private Size size;

    @Enumerated(EnumType.STRING)
    private Colour colour;

    private int qty;

}
