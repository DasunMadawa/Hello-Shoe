package lk.ijse.helloshoe.repo;

import lk.ijse.helloshoe.entity.Refund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefundRepo extends JpaRepository<Refund, String> {

}
