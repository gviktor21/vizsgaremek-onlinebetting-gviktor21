package com.gviktor.onlinebet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int participantId;
    private String name;
    private SportType sportType;
    @OneToMany(mappedBy = "participant")
    private List<SportParticipant> sportParticipant;
}
