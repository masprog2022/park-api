package com.masprogtechs.park.api.web.controller;

import com.masprogtechs.park.api.entity.Slot;
import com.masprogtechs.park.api.service.SlotService;
import com.masprogtechs.park.api.web.dto.SlotCreateDto;
import com.masprogtechs.park.api.web.dto.SlotResponseDto;
import com.masprogtechs.park.api.web.dto.mapper.SlotMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.*;
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/slots")
public class SlotController {

    private final SlotService slotService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> create(@RequestBody @Valid SlotCreateDto dto){
        Slot slot = SlotMapper.toSlot(dto);
        slotService.save(slot);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/{code}")
                .buildAndExpand(slot.getCode())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{code}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SlotResponseDto> getByCode(@PathVariable String code){
        Slot slot = slotService.findByCode(code);
        return ResponseEntity.ok(SlotMapper.toDto(slot));
    }

}
