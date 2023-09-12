package com.masprogtechs.park.api.service;

import com.masprogtechs.park.api.entity.User;
import com.masprogtechs.park.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Usuário não encontrado")
        );
    }

    @Transactional
    public User updatePassword(Long id, String currentPassword, String newPassword, String confirmPassword) {

        if(!newPassword.equals(confirmPassword)){
            throw new RuntimeException("Nova senha não confere com a confirmação de senha.");
        }

        User user = findById(id);
        if(!user.getPassword().equals(currentPassword)){
            throw new RuntimeException("Sua senha não confere.");
        }

        user.setPassword(newPassword);

        return user;
    }
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
