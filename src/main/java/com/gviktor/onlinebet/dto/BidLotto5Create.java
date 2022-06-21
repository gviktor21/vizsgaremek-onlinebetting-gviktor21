package com.gviktor.onlinebet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class BidLotto5Create {
    private int bidId;
    @NotBlank
    private int number1;
    @NotBlank
    private int number2;
    @NotBlank
    private int number3;
    @NotBlank
    private int number4;
    @NotBlank
    private int number5;
}
