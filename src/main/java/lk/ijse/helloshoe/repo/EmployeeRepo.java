package lk.ijse.helloshoe.repo;

import lk.ijse.helloshoe.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee , String> {

}
