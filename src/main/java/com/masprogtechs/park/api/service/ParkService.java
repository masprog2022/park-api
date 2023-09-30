package com.masprogtechs.park.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ParkService {

    private final CustomerSlotService customerSlotService;
    private final CustomerService customerService;
    private final SlotService slotService;
}
