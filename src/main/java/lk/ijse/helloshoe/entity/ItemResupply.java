package lk.ijse.helloshoe.entity;

import jakarta.persistence.*;
import lk.ijse.helloshoe.entity.compositeId.ItemResupplyId;
import lk.ijse.helloshoe.entity.enums.Colour;
import lk.ijse.helloshoe.entity.enums.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "item_resupply")
@Entity
@IdClass(ItemResupplyId.class)
public class ItemResupply {
    @ManyToOne
    @Id
    private Item item;

    @ManyToOne
    @Id
    private Resupply resupply;

    @Enumerated(EnumType.STRING)
    @Id
    private Size size;

    @Enumerated(EnumType.STRING)
    @Id
    private Colour colour;

    private int qty;

}
