package com.masprogtechs.park.api.web.controller;

import com.masprogtechs.park.api.entity.CustomerSlot;
import com.masprogtechs.park.api.service.ParkService;
import com.masprogtechs.park.api.web.dto.ParkCreateDto;
import com.masprogtechs.park.api.web.dto.ParkResponseDto;
import com.masprogtechs.park.api.web.dto.mapper.CustomerSlotMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/parks")
public class ParkController {

    private final ParkService parkService;

    @PostMapping("/check-in")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ParkResponseDto> checkin(@RequestBody @Valid ParkCreateDto dto){
        CustomerSlot customerSlot = CustomerSlotMapper.toCustomerSlot(dto);
        parkService.checkIn(customerSlot);
        ParkResponseDto responseDto = CustomerSlotMapper.toDto(customerSlot);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/{receipt}")
                .buildAndExpand(customerSlot.getReceipt())
                .toUri();

        return ResponseEntity.created(location).body(responseDto);
    }

}
