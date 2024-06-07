package lk.ijse.helloshoe.entity;

import jakarta.persistence.*;
import lk.ijse.helloshoe.entity.enums.Colour;
import lk.ijse.helloshoe.entity.enums.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @OneToMany(mappedBy = "itemSale")
    private List<Refund> refundList;


    @Enumerated(EnumType.STRING)
    private Size size;

    @Enumerated(EnumType.STRING)
    private Colour colour;

    private int qty;

}
