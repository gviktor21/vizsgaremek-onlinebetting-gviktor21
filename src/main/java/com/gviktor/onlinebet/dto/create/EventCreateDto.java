package com.gviktor.onlinebet.dto.create;

import com.gviktor.onlinebet.model.EventType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
@Data
@NoArgsConstructor
public class EventCreateDto {
    @NotNull
    private EventType eventType;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    @Length(max = 255,message = "Length cannot go beyond 255 characters.")
    private String description;
}
