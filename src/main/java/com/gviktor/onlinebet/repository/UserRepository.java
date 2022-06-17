package com.gviktor.onlinebet.repository;

import com.gviktor.onlinebet.model.BidAppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<BidAppUser,String> {
}
