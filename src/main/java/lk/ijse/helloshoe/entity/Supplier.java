package lk.ijse.helloshoe.entity;

import jakarta.persistence.*;
import lk.ijse.helloshoe.entity.enums.SupplierCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Supplier {
    @Id
    private String supplierId;
    private String supplierName;

    @Enumerated(EnumType.STRING)
    private SupplierCategory supplierCategory;

    private String mobileNo;
    private String landLineNo;

    @Column(unique = true)
    private String email;

    private String addressNoOrName;
    private String addressLane;
    private String addressState;
    private String addressCity;
    private String postalCode;
    private String country;

    @OneToMany(mappedBy = "supplier" , cascade = CascadeType.ALL , orphanRemoval = true , fetch = FetchType.LAZY)
    private List<Item> itemList;

}
