package lk.ijse.helloshoe.repo;


import lk.ijse.helloshoe.entity.Stock;
import lk.ijse.helloshoe.entity.enums.Colour;
import lk.ijse.helloshoe.entity.enums.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepo extends JpaRepository<Stock , String> {
    @Query(value = "SELECT * FROM stock WHERE colour = ?1 && size = ?2 && item_i_code =?3" , nativeQuery = true)
    Stock getItemStock(String colour, String size , String iCode);

}
