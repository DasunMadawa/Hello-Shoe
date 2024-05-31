package lk.ijse.helloshoe.entity;


import jakarta.persistence.*;
import lk.ijse.helloshoe.entity.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Sale {
    @Id
    private String oId;
    private int itemQty;
    private double totalPrice;
    @Temporal(TemporalType.DATE)
    private Date date;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private int points;

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    private Employee employee;

    @OneToMany(mappedBy = "sale" , cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<ItemSale> itemSaleList;



}
