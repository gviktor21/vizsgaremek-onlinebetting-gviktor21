package com.gviktor.onlinebet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
@Data
@NoArgsConstructor
public class BidAppUserShow {
    private String username;
    private String address;
    private LocalDate birthDate;
    private String gender;
    private String password;
    private long balance;
    private String email;
    private int accountLevel;
}
