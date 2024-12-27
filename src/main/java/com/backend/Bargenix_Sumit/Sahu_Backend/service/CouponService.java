package com.backend.Bargenix_Sumit.Sahu_Backend.service;

import com.backend.Bargenix_Sumit.Sahu_Backend.entity.Coupon;
import com.backend.Bargenix_Sumit.Sahu_Backend.entity.Product;
import com.backend.Bargenix_Sumit.Sahu_Backend.repository.CouponRepository;
import com.backend.Bargenix_Sumit.Sahu_Backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class CouponService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CouponRepository couponRepository;

    public String generateCoupon(Long productId,int validMinutes){

        Optional<Product>product=productRepository.findById(productId);

        // If there is no product regarding provided product id.
        if(product.isEmpty()){
            throw new IllegalArgumentException("Invalid Product ID");
        }
        Coupon coupon = new Coupon();

        coupon.setProduct(product.get());
        coupon.setCode(UUID.randomUUID().toString());
        coupon.setExpiryTime(LocalDateTime.now().plusMinutes(validMinutes));
        couponRepository.save(coupon);

        return  coupon.getCode();
    }

    public boolean validateCoupon(String code, Long productId){
        Optional<Coupon>coupon = couponRepository.findByCode(code);

        if(coupon.isEmpty()){
            throw new IllegalArgumentException("Invalid Coupon Code");
        }

        Coupon foundCoupon = coupon.get();

        if(!foundCoupon.getProduct().getId().equals(productId)){
            throw new IllegalArgumentException("Coupon not valid for this product");
        }

        // If the coupon for the product already used
        if (foundCoupon.getUsed()) {
            throw new IllegalArgumentException("Coupon has already been used");
        }


        // If the coupon for the product gets expired
        if (foundCoupon.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Coupon has expired");
        }


        foundCoupon.setUsed(true);
        couponRepository.save(foundCoupon);

        return true;

    }
}
