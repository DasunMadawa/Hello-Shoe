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
    private Date date;
    private String reason;

    @ManyToOne(fetch = FetchType.EAGER)
    private Employee employee;

    @OneToOne
    private ItemSale itemSale;

}
