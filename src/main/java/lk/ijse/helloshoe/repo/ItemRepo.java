package lk.ijse.helloshoe.repo;

import lk.ijse.helloshoe.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepo extends JpaRepository<Item , String> {

}
