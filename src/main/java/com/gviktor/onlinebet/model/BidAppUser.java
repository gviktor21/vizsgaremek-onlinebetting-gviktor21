package com.gviktor.onlinebet.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;

//todo change some types
@Entity
@Data
@NoArgsConstructor
public class BidAppUser {
    @Id
    private String username;
    private String address;
    private LocalDate birthDate;
    private String gender;
    private String password;
    private long balance;
    private String email;
    private int accountLevel;
    @OneToMany(mappedBy="user")
    private List<Bid> userBids;
}
