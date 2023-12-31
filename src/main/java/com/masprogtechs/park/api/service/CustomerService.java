package com.masprogtechs.park.api.service;
import com.masprogtechs.park.api.exception.EntityRuntimeException;
import com.masprogtechs.park.api.entity.Customer;
import com.masprogtechs.park.api.exception.BiUniqueViolationException;
import com.masprogtechs.park.api.repository.CustomerRepository;
import com.masprogtechs.park.api.repository.projection.CustomerProjection;
//import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;



    @Transactional
    public Customer save(Customer customer){
        try{

            return customerRepository.save(customer);

        }catch (DataIntegrityViolationException ex){
          throw  new BiUniqueViolationException(String.format("BI %s não pode ser cadastrado, já existe no sistema", customer.getBi()));
        }
    }

    @Transactional(readOnly = true)
    public Customer findById(Long id) {
        return customerRepository.findById(id).orElseThrow(
                () -> new EntityRuntimeException(String.format("Cliente %s não encontrado no sistema", id))
        );
    }
      @Transactional(readOnly = true)
    public Page<CustomerProjection> findAll(Pageable pageable) {
        return customerRepository.findAllPageable(pageable);
    }

    @Transactional(readOnly = true)
    public  Customer findByUserId(Long id) {
        return customerRepository.findByUserId(id);
    }

    @Transactional(readOnly = true)
    public Customer findByBi(String bi) {
        return customerRepository.findByBi(bi).orElseThrow(
                ()-> new EntityRuntimeException(String.format("Cliente com BI %s não encontrado", bi))
        );
    }







}
