package test.coffemachine.repositories;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import test.coffemachine.model.entities.ProductEntity;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

	@Query("SELECT SUM(price) as totalAmount FROM product WHERE id IN (:ids)")
	BigDecimal getTotalAmount(@Param(value = "ids") List<Long> ids);


}
