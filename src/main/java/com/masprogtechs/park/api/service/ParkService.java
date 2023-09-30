package com.masprogtechs.park.api.service;

import com.masprogtechs.park.api.entity.Customer;
import com.masprogtechs.park.api.entity.CustomerSlot;
import com.masprogtechs.park.api.entity.Slot;
import com.masprogtechs.park.api.enums.StatusSlot;
import com.masprogtechs.park.api.util.ParkUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class ParkService {

    private final CustomerSlotService customerSlotService;
    private final CustomerService customerService;
    private final SlotService slotService;

    @Transactional
    public CustomerSlot checkIn(CustomerSlot customerSlot){

        Customer customer = customerService.findByCpf(customerSlot.getCustomer().getCpf());
        customerSlot.setCustomer(customer);

        Slot slot = slotService.findBySlotFree();
        slot.setStatus(StatusSlot.BUSY);
        customerSlot.setSlot(slot);

        customerSlot.setInputData(LocalDateTime.now());
        customerSlot.setReceipt(ParkUtils.generateReceipt());

        return customerSlotService.save(customerSlot);

    }
}
