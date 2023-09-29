package com.masprogtechs.park.api.web.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SlotResponseDto {

    private Long id;
    private String code;
    private  String status;

}
