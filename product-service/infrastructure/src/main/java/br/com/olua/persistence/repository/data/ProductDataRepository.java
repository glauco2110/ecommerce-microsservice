package br.com.olua.persistence.repository.data;

import br.com.olua.persistence.data.ProductData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDataRepository extends JpaRepository<ProductData,String> {
}
