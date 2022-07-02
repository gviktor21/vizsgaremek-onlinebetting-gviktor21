package com.gviktor.onlinebet.dto.create;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class Lotto5CreateDto {

    @NotNull
    private Integer eventId;
    @Min(value=1)
    @Max(value=90)
    private Integer number1;
    @Min(value=1)
    @Max(value=90)
    private Integer number2;
    @Min(value=1)
    @Max(value=90)
    private Integer number3;
    @Min(value=1)
    @Max(value=90)
    private Integer number4;
    @Min(value=1)
    @Max(value=90)
    private Integer number5;
}
