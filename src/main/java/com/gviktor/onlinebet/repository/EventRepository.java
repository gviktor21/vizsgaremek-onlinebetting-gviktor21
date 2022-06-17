package com.gviktor.onlinebet.repository;

import com.gviktor.onlinebet.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {
}
