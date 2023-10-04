package com.masprogtechs.park.api.service;

import com.masprogtechs.park.api.entity.CustomerSlot;
import com.masprogtechs.park.api.exception.EntityRuntimeException;
import com.masprogtechs.park.api.repository.CustomerSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CustomerSlotService {
    private final CustomerSlotRepository repository;

    @Transactional
    public CustomerSlot save(CustomerSlot customerSlot){
        return repository.save(customerSlot);
    }

    @Transactional(readOnly = true)
    public CustomerSlot findByReceipt(String receipt) {
        return repository.findByReceiptAndOutputDataIsNull(receipt).orElseThrow(
                () -> new EntityRuntimeException(String.format(
                        "Recibo %s não encontrado no sistema ou check-out já realizado", receipt
                ))
        );
    }

    @Transactional(readOnly = true)
    public long getTotalOfTimesParkCompleted(String cpf) {
        return repository.countByCustomerCpfAndOutputDataIsNotNull(cpf);
    }
}
