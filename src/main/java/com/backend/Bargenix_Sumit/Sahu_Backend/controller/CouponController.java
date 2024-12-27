package com.backend.Bargenix_Sumit.Sahu_Backend.controller;

import com.backend.Bargenix_Sumit.Sahu_Backend.entity.Coupon;
import com.backend.Bargenix_Sumit.Sahu_Backend.entity.Log;
import com.backend.Bargenix_Sumit.Sahu_Backend.logs.LogService;
import com.backend.Bargenix_Sumit.Sahu_Backend.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/coupons")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @Autowired
    private LogService logService;

    @PostMapping("/generate")
    public ResponseEntity<String> generateCoupon(@RequestParam Long productId,@RequestParam int validMinutes){

        try {
            String couponCode = couponService.generateCoupon(productId, validMinutes);

            // Save log for coupon generation
            Log log = new Log();
            log.setRequestType("POST");
            log.setDetails("Generated coupon for product ID: " + productId + ", valid for " + validMinutes + " minutes. Coupon Code: " + couponCode);
            logService.saveLog(log);

            return ResponseEntity.ok("Coupon Code: " + couponCode);
        } catch (IllegalArgumentException e) {
            // Save log for error
            Log log = new Log();
            log.setRequestType("POST");
            log.setDetails("Failed to generate coupon for product ID: " + productId + ". Error: " + e.getMessage());
            logService.saveLog(log);

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<String> validateCoupon(@RequestParam String code, @RequestParam Long productId) {
        try {
            boolean isValid = couponService.validateCoupon(code, productId);

            // Save log for coupon validation
            Log log = new Log();
            log.setRequestType("GET");
            log.setDetails("Validated coupon code: " + code + " for product ID: " + productId + ". Result: " + (isValid ? "Valid" : "Invalid"));
            logService.saveLog(log);

            return ResponseEntity.ok(isValid ? "Valid coupon" : "Invalid coupon");
        } catch (IllegalArgumentException e) {
            // Save log for error
            Log log = new Log();
            log.setRequestType("GET");
            log.setDetails("Failed to validate coupon code: " + code + " for product ID: " + productId + ". Error: " + e.getMessage());
            logService.saveLog(log);

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/logs")
    public ResponseEntity<List<Log>> getAllLogs() {
        try {
            List<Log> logs = logService.getAllLogs();
            return ResponseEntity.ok(logs);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

}
