package com.example.userwallet.mapper;

import com.example.userwallet.entitiy.UserWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserWalletRepository extends JpaRepository<UserWallet, Long> {
    UserWallet findByUserId(Long userId);
}

