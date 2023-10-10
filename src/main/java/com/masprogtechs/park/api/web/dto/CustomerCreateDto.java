package com.masprogtechs.park.api.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerCreateDto {

    @NotNull
    @Size(min = 5, max = 100)
    private String name;

    @Size(min = 12, max = 12)
    @Pattern(regexp = "^\\d{7}[A-Z]{2}\\d{3}$", message = "Bilhete de entidade deve ter este padrão OOOOOOOXX000")
    private String bi;
}
