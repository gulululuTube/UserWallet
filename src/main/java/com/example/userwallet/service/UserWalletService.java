package com.example.userwallet.service;

import com.example.userwallet.entitiy.UserWallet;
import com.example.userwallet.entitiy.WalletTransaction;
import com.example.userwallet.mapper.UserWalletRepository;
import com.example.userwallet.mapper.WalletTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserWalletService {
    private UserWalletRepository userWalletRepository;
    private WalletTransactionRepository walletTransactionRepository;

    @Autowired
    public UserWalletService(UserWalletRepository userWalletRepository, WalletTransactionRepository walletTransactionRepository) {
        this.userWalletRepository = userWalletRepository;
        this.walletTransactionRepository = walletTransactionRepository;
    }

    //钱余额
    public BigDecimal getUserWalletBalance(Long userId) {
        UserWallet userWallet = userWalletRepository.findByUserId(userId);
        if (userWallet != null) {
            return userWallet.getBalance();
        }
        return BigDecimal.ZERO;
    }

    //用户消费
    public void userExpense(Long userId, BigDecimal amount) {
        UserWallet userWallet = userWalletRepository.findByUserId(userId);
        if (userWallet != null) {
            BigDecimal balance = userWallet.getBalance();
            userWallet.setBalance(balance.subtract(amount));
            userWalletRepository.save(userWallet);
            insertTransaction(userId, "Expense", amount);
        }
    }

    //退款
    public void userRefund(Long userId, BigDecimal amount) {
        UserWallet userWallet = userWalletRepository.findByUserId(userId);
        if (userWallet != null) {
            BigDecimal balance = userWallet.getBalance();
            userWallet.setBalance(balance.add(amount));
            userWalletRepository.save(userWallet);
            insertTransaction(userId, "Refund", amount);
        }
    }

    public List<WalletTransaction> getUserWalletTransactions(Long userId) {
        return walletTransactionRepository.findByUserId(userId);
    }

    //用户钱包金额变动明细
    private void insertTransaction(Long userId, String transactionType, BigDecimal amount) {
        WalletTransaction transaction = new WalletTransaction();
        transaction.setUserId(userId);
        transaction.setTransactionType(transactionType);
        transaction.setAmount(amount);
        walletTransactionRepository.save(transaction);
    }
}

