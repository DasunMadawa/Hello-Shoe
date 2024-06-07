package lk.ijse.helloshoe.service;

import lk.ijse.helloshoe.reqAndResp.response.JWTAuthResponse;
import lk.ijse.helloshoe.reqAndResp.secure.SignIn;
import lk.ijse.helloshoe.reqAndResp.secure.SignUp;

public interface AuthenticationService {
    JWTAuthResponse signIn(SignIn signIn);
    JWTAuthResponse signUp(SignUp signUp);
    JWTAuthResponse refreshToken(String accessToken);


}
