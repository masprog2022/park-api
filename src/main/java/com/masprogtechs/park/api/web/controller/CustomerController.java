package com.masprogtechs.park.api.web.controller;

import com.masprogtechs.park.api.entity.Customer;
import com.masprogtechs.park.api.jwt.JwtUserDetails;
import com.masprogtechs.park.api.repository.CustomerRepository;
import com.masprogtechs.park.api.service.CustomerService;
import com.masprogtechs.park.api.service.UserService;
import com.masprogtechs.park.api.web.dto.CustomerCreateDto;
import com.masprogtechs.park.api.web.dto.CustomerResponseDto;
import com.masprogtechs.park.api.web.dto.mapper.CustomerMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    private final UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<CustomerResponseDto> create(@RequestBody @Valid CustomerCreateDto dto,
                                           @AuthenticationPrincipal JwtUserDetails userDetails){
        Customer customer = CustomerMapper.toCustomer(dto);
        customer.setUser(userService.findById(userDetails.getId()));

        customerService.save(customer);

        return ResponseEntity.status(201).body(CustomerMapper.toDto(customer));

    }
}
