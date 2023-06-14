package com.example.userwallet.controller;

import com.example.userwallet.entitiy.WalletTransaction;
import com.example.userwallet.service.UserWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/wallet")
public class UserWalletController {
    private UserWalletService userWalletService;

    @Autowired
    public UserWalletController(UserWalletService userWalletService) {
        this.userWalletService = userWalletService;
    }

    @GetMapping("/{userId}/balance")
    public BigDecimal getUserWalletBalance(@PathVariable Long userId) {
        return userWalletService.getUserWalletBalance(userId);
    }

    @PostMapping("/{userId}/expense")
    public void userExpense(@PathVariable Long userId, @RequestBody BigDecimal amount) {
        userWalletService.userExpense(userId, amount);
    }

    @PostMapping("/{userId}/refund")
    public void userRefund(@PathVariable Long userId, @RequestBody BigDecimal amount) {
        userWalletService.userRefund(userId, amount);
    }

    @GetMapping("/{userId}/transactions")
    public List<WalletTransaction> getUserWalletTransactions(@PathVariable Long userId) {
        return userWalletService.getUserWalletTransactions(userId);
    }
}
