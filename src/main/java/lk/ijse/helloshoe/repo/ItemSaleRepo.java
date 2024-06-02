package lk.ijse.helloshoe.repo;

import lk.ijse.helloshoe.entity.ItemSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemSaleRepo extends JpaRepository<ItemSale , String> {

}
