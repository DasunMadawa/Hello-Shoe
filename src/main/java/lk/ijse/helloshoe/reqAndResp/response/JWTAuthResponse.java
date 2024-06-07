package lk.ijse.helloshoe.reqAndResp.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class JWTAuthResponse {
    @NonNull
    private String token;


}
