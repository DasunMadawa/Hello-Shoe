package lk.ijse.helloshoe.entity;

import jakarta.persistence.*;
import lk.ijse.helloshoe.entity.enums.Gender;
import lk.ijse.helloshoe.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Employee implements UserDetails {
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

    @Temporal(TemporalType.DATE)
    private Date birthday;
    @Temporal(TemporalType.DATE)
    private Date joinDate;
    private String branch;
    private String contactNo;

    private String password;

    @Column(unique = true)
    private String email;

    private String addressNoOrName;
    private String addressLane;
    private String addressCity;
    private String addressState;
    private String postalCode;

    private String emergencyContactPerson;
    private String emergencyContactNumber;

    @OneToMany(mappedBy = "employee" , cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<Sale> saleList;

    @OneToMany(mappedBy = "employee" , cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<Refund> refundList;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + accessRole.name() ));
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
