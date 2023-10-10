package com.masprogtechs.park.api.service;

import com.masprogtechs.park.api.entity.CustomerSlot;
import com.masprogtechs.park.api.exception.EntityRuntimeException;
import com.masprogtechs.park.api.repository.CustomerSlotRepository;
import com.masprogtechs.park.api.repository.projection.CustomerSlotProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public long getTotalOfTimesParkCompleted(String bi) {
        return repository.countByCustomerBiAndOutputDataIsNotNull(bi);
    }

     @Transactional(readOnly = true)
     public Page<CustomerSlotProjection> findAllByCustomerBi(String bi, Pageable pageable) {
        return repository.findAllByCustomerBi(bi, pageable);
    }

    @Transactional(readOnly = true)
    public Page<CustomerSlotProjection> findAllByUserId(Long id, Pageable pageable) {
        return repository.findAllByCustomerUserId(id, pageable);
    }
}
