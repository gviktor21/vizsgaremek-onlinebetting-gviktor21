package com.gviktor.onlinebet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class SportEvent {
    @Id
    private int eventId;
    @ManyToOne
    @JoinColumn(foreignKey=@ForeignKey(name="fk_sport_event_winner"))
    private SportParticipant winner;
    @OneToMany(mappedBy = "event")
    private List<SportParticipant> eventParticipants;
    @Enumerated
    private SportType sportType;
}
