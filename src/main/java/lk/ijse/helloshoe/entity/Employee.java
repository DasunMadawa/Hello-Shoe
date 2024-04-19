package lk.ijse.helloshoe.entity;

import jakarta.persistence.*;
import lk.ijse.helloshoe.entity.enums.Gender;
import lk.ijse.helloshoe.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Employee {
    @Id
    private String employeeId;
    private String employeeName;

    @Column(columnDefinition = "LONGTEXT")
    private String profilePic;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String status;
    private String designation;

    @Enumerated(EnumType.STRING)
    private Role accessRole;

    private Date birthday;
    private Date joinDate;
    private String branch;
    private String contactNo;

    @Column(unique = true)
    private String email;

    private String addressNoOrName;
    private String addressLane;
    private String addressCity;
    private String addressState;
    private String postalCode;

    private String password;

    private String emergencyContactPerson;
    private String emergencyContactNumber;

    @OneToMany(mappedBy = "employee" , cascade = CascadeType.ALL , orphanRemoval = true , fetch = FetchType.LAZY)
    private List<Sale> saleList;

    @OneToMany(mappedBy = "employee" , cascade = CascadeType.ALL , orphanRemoval = true , fetch = FetchType.LAZY)
    private List<Refund> refundList;



}
