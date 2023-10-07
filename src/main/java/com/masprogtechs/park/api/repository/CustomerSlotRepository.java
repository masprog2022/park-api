package com.masprogtechs.park.api.repository;

import com.masprogtechs.park.api.entity.CustomerSlot;
import com.masprogtechs.park.api.repository.projection.CustomerSlotProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerSlotRepository extends JpaRepository<CustomerSlot, Long> {
    Optional<CustomerSlot> findByReceiptAndOutputDataIsNull(String receipt);

    long countByCustomerCpfAndOutputDataIsNotNull(String cpf);

    Page<CustomerSlotProjection> findAllByCustomerCpf(String cpf, Pageable pageable);

    Page<CustomerSlotProjection> findAllByCustomerUserId(Long id, Pageable pageable);
}
