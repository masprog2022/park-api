package com.masprogtechs.park.api.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParkResponseDto {

    private String plate;
    private String make;
    private String model;
    private String color;
    private String customerCpf;
    private String receipt;
    private LocalDateTime inputData;
    private LocalDateTime outputData;
    private String slotCode;
    private BigDecimal value;
    private BigDecimal discount;
}
