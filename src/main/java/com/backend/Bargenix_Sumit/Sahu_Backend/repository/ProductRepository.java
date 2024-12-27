package com.backend.Bargenix_Sumit.Sahu_Backend.repository;

import com.backend.Bargenix_Sumit.Sahu_Backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
    
}
