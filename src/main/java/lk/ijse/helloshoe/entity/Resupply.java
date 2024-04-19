package lk.ijse.helloshoe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Resupply {
    @Id
    private String resupplyId;
    private String date;
    private double totalValue;
    private int totalQty;

    @OneToMany(mappedBy = "resupply" , cascade = CascadeType.ALL , orphanRemoval = true , fetch = FetchType.EAGER)
    private List<ItemResupply> itemResupplyList;

}
