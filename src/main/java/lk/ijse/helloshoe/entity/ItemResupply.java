package lk.ijse.helloshoe.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
public class ItemResupply {
    @ManyToOne
    @Id
    private Item item;

    @ManyToOne
    @Id
    private Resupply resupply;

    @Id
    private Size size;

    @Id
    private Colour colour;

    private int qty;

}
