package com.masprogtechs.park.api.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkCreateDto {

    @NotBlank
    @Size(min = 11, max = 11)
    @Pattern(regexp = "^[A-Z]{2}-\\d{2}-\\d{2}-[A-Z]{2}$", message = "A matricula do carro deve seguir o padrão 'XX-00-00-YY'")
    private String plate;

    @NotBlank
    private String make;

    @NotBlank
    private String model;

    @NotBlank
    private String color;

    @NotBlank
    @Size(min = 12, max = 12)
    @Pattern(regexp = "^\\d{7}[A-Z]{2}\\d{3}$", message = "Bilhete de entidade deve ter este padrão OOOOOOOXX000")
    private String customerBi;

}
