package com.gviktor.onlinebet.repository;

import com.gviktor.onlinebet.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant,Integer> {
}
