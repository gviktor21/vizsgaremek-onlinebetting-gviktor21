package com.gviktor.onlinebet.dto.create;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class BidLotto5CreateDto {
    private int bidId;
    @Min(value=1)
    @Max(value=90)
    private int number1;
    @Min(value=1)
    @Max(value=90)
    private int number2;
    @Min(value=1)
    @Max(value=90)
    private int number3;
    @Min(value=1)
    @Max(value=90)
    private int number4;
    @Min(value=1)
    @Max(value=90)
    private int number5;
}
