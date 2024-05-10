package lk.ijse.helloshoe.repo;

import lk.ijse.helloshoe.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer , String> {

}
