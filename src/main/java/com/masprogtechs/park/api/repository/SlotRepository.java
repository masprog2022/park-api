package com.masprogtechs.park.api.repository;

import com.masprogtechs.park.api.entity.Slot;
import com.masprogtechs.park.api.enums.StatusSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SlotRepository extends JpaRepository<Slot, Long> {
    Optional<Slot> findByCode(String code);

   Optional<Slot> findFirstByStatus(StatusSlot statusSlot);
}
