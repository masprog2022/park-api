package com.masprogtechs.park.api.service;


import com.masprogtechs.park.api.entity.User;
import com.masprogtechs.park.api.enums.Role;
import com.masprogtechs.park.api.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomerUserService {


    private final UserRepository userRepository;

    @Transactional
    public User save(User user) {
        user.setRole(Role.ROLE_CUSTOMER);
        return userRepository.save(user);
    }
}
