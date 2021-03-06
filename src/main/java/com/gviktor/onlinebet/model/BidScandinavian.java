package com.gviktor.onlinebet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class BidScandinavian {
    @Id
    private int bidId;
    private int matches;
    private int number1;
    private int number2;
    private int number3;
    private int number4;
    private int number5;
    private int number6;
    private int number7;
    private int matchesComputer;
    private int computerNumber1;
    private int computerNumber2;
    private int computerNumber3;
    private int computerNumber4;
    private int computerNumber5;
    private int computerNumber6;
    private int computerNumber7;
}
