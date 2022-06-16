package com.gviktor.onlinebet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class BidLotto6 {
    @Id
    private int bidId;
    private int matches;
    private int number1;
    private int number2;
    private int number3;
    private int number4;
    private int number5;
    private int number6;
}
