package com.masprogtechs.park.api.service;

import com.masprogtechs.park.api.entity.Customer;
import com.masprogtechs.park.api.exception.CpfUniqueViolationException;
import com.masprogtechs.park.api.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional
    public Customer save(Customer customer){
        try{

            return customerRepository.save(customer);

        }catch (DataIntegrityViolationException ex){
          throw  new CpfUniqueViolationException(String.format("CPF %s não pode ser cadastrado, já existe no sistema", customer.getCpf()));
        }
    }

    @Transactional(readOnly = true)
    public Customer findById(Long id) {
        return customerRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Cliente %s não encontrado no sistema", id))
        );
    }
      @Transactional(readOnly = true)
    public Page<Customer> findAll(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }
}
