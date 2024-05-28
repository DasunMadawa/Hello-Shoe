package lk.ijse.helloshoe.entity;

import jakarta.persistence.*;
import lk.ijse.helloshoe.entity.enums.Category;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
//@Getter
//@Setter
public class Item {
    @Id
    private String iCode;
    private String description;

    @Enumerated(EnumType.STRING)
    private Category category;

    private double priceBuy;
    private double priceSell;

    @OneToMany(mappedBy = "item" , cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<ItemSale> itemSaleList;

    @ManyToOne(fetch = FetchType.LAZY)
    private Supplier supplier;

    @OneToMany(mappedBy = "item" , cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<Stock> stockList;

    @OneToMany(mappedBy = "item" , cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<ItemResupply> itemResupplyList;


}
