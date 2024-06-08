package lk.ijse.helloshoe.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lk.ijse.helloshoe.entity.enums.Gender;
import lk.ijse.helloshoe.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeDTO {
    private String employeeId;
    @NotNull
    private String employeeName;

    private String profilePic;

    @NotNull
    private Gender gender;
    private String status;
    private String designation;

    @NotNull
    private Role accessRole;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date joinDate;
    private String branch;
    private String contactNo;

    @Email
    private String email;
    private String password;

    private String addressNoOrName;
    private String addressLane;
    private String addressCity;
    private String addressState;
    private String postalCode;

    private String emergencyContactPerson;
    private String emergencyContactNumber;

}

