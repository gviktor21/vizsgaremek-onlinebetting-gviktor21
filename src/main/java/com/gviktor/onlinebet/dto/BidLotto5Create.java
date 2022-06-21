package com.gviktor.onlinebet.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class BidLotto5Create {
    private int bidId;
    @NotBlank
    @Min(value=1)
    @Max(value=90)
    private int number1;
    @NotBlank
    @Min(value=1)
    @Max(value=90)
    private int number2;
    @NotBlank
    @Min(value=1)
    @Max(value=90)
    private int number3;
    @NotBlank
    @Min(value=1)
    @Max(value=90)
    private int number4;
    @NotBlank
    @Min(value=1)
    @Max(value=90)
    private int number5;
}
