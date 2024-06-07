package lk.ijse.helloshoe.service.impl;
import lk.ijse.helloshoe.repo.EmployeeRepo;
import lk.ijse.helloshoe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final EmployeeRepo employeeRepo;

    @Override
    public UserDetailsService userDetailsService() {
        return username -> employeeRepo.findByEmail(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User Not Found")
                );

    }


}
