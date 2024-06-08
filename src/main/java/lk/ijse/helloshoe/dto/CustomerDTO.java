package lk.ijse.helloshoe.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lk.ijse.helloshoe.entity.enums.Gender;
import lk.ijse.helloshoe.entity.enums.LoyaltyLevel;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDTO {
    @JsonProperty("cId")
    private String cId;
    @JsonProperty("cName")
    @NotNull
    private String cName;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Gender gender;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private Date joinDate;

    @Enumerated(EnumType.STRING)
    private LoyaltyLevel level;

    private int totalPoints;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    @NotNull
    private String contactNo;

    @Column(unique = true)
    private String email;

    private String addressNoOrName;
    private String addressLane;
    private String addressCity;
    private String addressState;
    private String postalCode;

}

