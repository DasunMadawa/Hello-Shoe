package lk.ijse.helloshoe.repo;

import lk.ijse.helloshoe.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepo extends JpaRepository<Customer , String> {
    @Query("SELECT c FROM Customer c WHERE FUNCTION('MONTH', c.birthday) = :month AND FUNCTION('DAY', c.birthday) = :day")
    List<Customer> findByBirthdayMonthAndDay(@Param("month") int month, @Param("day") int day);

}
