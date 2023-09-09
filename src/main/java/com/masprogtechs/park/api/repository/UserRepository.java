package com.masprogtechs.park.api.repository;

import com.masprogtechs.park.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}