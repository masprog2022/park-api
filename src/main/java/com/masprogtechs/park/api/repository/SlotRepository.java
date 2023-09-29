package com.masprogtechs.park.api.repository;

import com.masprogtechs.park.api.entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SlotRepository extends JpaRepository<Slot, Long> {
}
