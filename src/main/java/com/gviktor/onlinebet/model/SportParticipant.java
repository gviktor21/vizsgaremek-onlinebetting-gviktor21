package com.gviktor.onlinebet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class SportParticipant {
    @Id
    int id;
    @ManyToOne
    @JoinColumn(name="eventId",foreignKey=@ForeignKey(name="fk_sportEvent_sportParticipant"))
    private  SportEvent event;
    @ManyToOne
    @JoinColumn(name="participantId",foreignKey=@ForeignKey(name="fk_sportEvent_participant"))
    private Participant participant;

    private int multiplier;
}
