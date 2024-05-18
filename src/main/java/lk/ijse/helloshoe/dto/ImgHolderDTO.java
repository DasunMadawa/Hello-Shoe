package lk.ijse.helloshoe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImgHolderDTO {
    private String imgId;
    private String generatedImgId;
    private String img;

}

