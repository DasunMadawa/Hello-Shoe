package lk.ijse.helloshoe.repo;

import lk.ijse.helloshoe.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee , String> {
    Optional<Employee> findByEmail(String email);

}
