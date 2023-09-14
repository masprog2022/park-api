package com.masprogtechs.park.api.service;

import com.masprogtechs.park.api.entity.User;
import com.masprogtechs.park.api.exception.EntityRuntimeException;
import com.masprogtechs.park.api.exception.PasswordInvalidException;
import com.masprogtechs.park.api.exception.UsernameUniqueViolationException;
import com.masprogtechs.park.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User save(User user) {
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException ex){
            throw new UsernameUniqueViolationException(String.format("Username %s já cadastrado", user.getUsername()));
        }

    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityRuntimeException(String.format("Usuário %s não encontrado.", id)
        ));
    }

    @Transactional
    public User updatePassword(Long id, String currentPassword, String newPassword, String confirmPassword) {

        if(!newPassword.equals(confirmPassword)){
            throw new PasswordInvalidException("Nova senha não confere com a confirmação de senha.");
        }

        User user = findById(id);
        if(!user.getPassword().equals(currentPassword)){
            throw new PasswordInvalidException("Sua senha não confere.");
        }

        user.setPassword(newPassword);

        return user;
    }
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
