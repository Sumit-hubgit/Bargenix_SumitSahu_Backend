package com.backend.Bargenix_Sumit.Sahu_Backend.repository;

import com.backend.Bargenix_Sumit.Sahu_Backend.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon,Long> {
    Optional<Coupon> findByCode(String code);
}
