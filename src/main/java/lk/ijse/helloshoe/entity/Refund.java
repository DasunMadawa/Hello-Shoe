package lk.ijse.helloshoe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Refund {
    @Id
    private String rId;
    private double value;
    @Temporal(TemporalType.DATE)
    private Date date;
    private String reason;
    private int qty;

    @ManyToOne(fetch = FetchType.EAGER)
    private Employee employee;

    @ManyToOne
    private ItemSale itemSale;

}
