package com.masprogtechs.park.api.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserLoginDto {

    @NotBlank
    @Email(message = "formato do email é inválido", regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")
    private String username;

    @NotBlank
    @Size(min = 6, max = 6)
    private String password;

}
