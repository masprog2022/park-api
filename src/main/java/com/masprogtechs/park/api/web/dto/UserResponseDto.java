package com.masprogtechs.park.api.web.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserResponseDto {

    private Long id;
    private String username;
    private String role;
}
