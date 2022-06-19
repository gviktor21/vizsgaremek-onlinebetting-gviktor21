package com.gviktor.onlinebet.repository;

import com.gviktor.onlinebet.model.SportEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SportEventRepository extends JpaRepository<SportEvent,Integer> {
}
