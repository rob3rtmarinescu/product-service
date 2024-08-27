package ing.product_service.repository;

import ing.product_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDao extends JpaRepository<Product, Long> {


    @Modifying
    @Query("update Product p set p.price =:price where p.id =:productId")
    void updatePrice(Long productId, Double price);
}