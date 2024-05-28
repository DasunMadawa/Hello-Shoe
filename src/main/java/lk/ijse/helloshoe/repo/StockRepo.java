package lk.ijse.helloshoe.repo;


import lk.ijse.helloshoe.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepo extends JpaRepository<Stock , String> {
//    public Stock getItemStock

}
