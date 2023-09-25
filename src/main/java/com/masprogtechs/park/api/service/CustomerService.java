package com.masprogtechs.park.api.service;

import com.masprogtechs.park.api.entity.Customer;
import com.masprogtechs.park.api.exception.CpfUniqueViolationException;
import com.masprogtechs.park.api.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
