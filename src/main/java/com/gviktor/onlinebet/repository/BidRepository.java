package com.gviktor.onlinebet.repository;

import com.gviktor.onlinebet.model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidRepository extends JpaRepository<Bid,Integer> {
}
