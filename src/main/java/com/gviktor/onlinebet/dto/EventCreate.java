package com.gviktor.onlinebet.dto;

import com.gviktor.onlinebet.model.EventType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
@Data
@NoArgsConstructor
public class EventCreate {
    @NotBlank
    @NotNull
    private EventType eventType;
    @NotBlank
    @NotNull
    private LocalDate startDate;
    @NotBlank
    @NotNull
    private LocalDate endDate;
    @Length(max = 255,message = "Length cannot go beyond 255 characters.")
    private String description;
}
