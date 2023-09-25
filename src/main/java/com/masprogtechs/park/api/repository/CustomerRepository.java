package com.masprogtechs.park.api.repository;

import com.masprogtechs.park.api.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
