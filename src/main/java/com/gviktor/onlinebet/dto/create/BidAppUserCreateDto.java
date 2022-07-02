package com.gviktor.onlinebet.dto.create;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class BidAppUserCreateDto {
    @NotBlank
    @NotNull
    @Size(min = 5, max = 50, message = "Username remain between 5 and 50 characters")
    private String username;
    private String address;
    private LocalDate birthDate;
    private String gender;
    @NotBlank
    @NotNull
    @Size(min = 5, max = 20, message = "Password should remain between 5 and 50 characters")
    private String password;
    @PositiveOrZero
    private long balance;
    @Email
    private String email;
    @Min(value =1)
    @Max(value=3)
    private int accountLevel;
}
