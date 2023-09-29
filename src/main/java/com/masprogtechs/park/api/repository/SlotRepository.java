package com.masprogtechs.park.api.repository;

import com.masprogtechs.park.api.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SlotRepository extends JpaRepository<Slot, Long> {
    Optional<Slot> findByCode(String code);
}
