package com.masprogtechs.park.api.repository;

import com.masprogtechs.park.api.entity.CustomerSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerSlotRepository extends JpaRepository<CustomerSlot, Long> {
    Optional<CustomerSlot> findByReceiptAndOutputDataIsNull(String receipt);

    long countByCustomerCpfAndOutputDataIsNotNull(String cpf);
}
