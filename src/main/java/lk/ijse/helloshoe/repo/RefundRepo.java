package lk.ijse.helloshoe.repo;

import lk.ijse.helloshoe.entity.Refund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefundRepo extends JpaRepository<Refund, String> {

    @Query(value = "SELECT * FROM refund WHERE item_sale_item_sale_id = ?1" , nativeQuery = true)
    List<Refund> getRefundByItemSaleId(String itemSaleId);

    @Query(value = "SELECT * FROM refund WHERE item_sale_item_sale_id LIKE %?1%" , nativeQuery = true)
    List<Refund> getRefundsBySaleId(String saleId);

}
