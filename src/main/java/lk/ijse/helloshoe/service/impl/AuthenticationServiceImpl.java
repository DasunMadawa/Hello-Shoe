package lk.ijse.helloshoe.service.impl;

import lk.ijse.helloshoe.entity.Employee;
import lk.ijse.helloshoe.entity.User;
import lk.ijse.helloshoe.repo.EmployeeRepo;
import lk.ijse.helloshoe.repo.UserRepo;
import lk.ijse.helloshoe.reqAndResp.response.JWTAuthResponse;
import lk.ijse.helloshoe.reqAndResp.secure.SignIn;
import lk.ijse.helloshoe.reqAndResp.secure.SignUp;
import lk.ijse.helloshoe.service.AuthenticationService;
import lk.ijse.helloshoe.service.JWTService;
import lk.ijse.helloshoe.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepo userRepo;
    private final JWTService jwtService;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public JWTAuthResponse signIn(SignIn signIn) {
        System.out.println("IN");
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signIn.getEmail(), signIn.getPassword())
        );
        System.out.println("out");


        User userByEmail = userRepo.findByEmail(signIn.getEmail())
                .orElseThrow(
                        () -> new UsernameNotFoundException("User Not Found")
                );

        String token = jwtService.generateToken(userByEmail);

        return new JWTAuthResponse(token);

    }

    @Override
    public JWTAuthResponse signUp(SignUp signUp) {
        User userByEmail = userRepo.findByEmail(signUp.getEmail())
                .orElseThrow(
                        () -> new UsernameNotFoundException("User Not Found")
                );

        if (userByEmail != null) {
            userByEmail.setPassword(passwordEncoder.encode(signUp.getPassword()));
            userRepo.save(userByEmail);

            String token = jwtService.generateToken(userByEmail);
            return new JWTAuthResponse(token);

        } else {
            throw new UsernameNotFoundException("User Not Found");

        }

    }

    @Override
    public JWTAuthResponse refreshToken(String accessToken) {
        String userName = jwtService.extractUserName(accessToken);

        User user = userRepo.findByEmail(userName).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found")
        );

        String token = jwtService.generateToken(user);
        return new JWTAuthResponse(token);

    }

}
