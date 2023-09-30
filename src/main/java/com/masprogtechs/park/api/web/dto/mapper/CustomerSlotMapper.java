package com.masprogtechs.park.api.web.dto.mapper;

import com.masprogtechs.park.api.entity.CustomerSlot;
import com.masprogtechs.park.api.web.dto.ParkCreateDto;
import com.masprogtechs.park.api.web.dto.ParkResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerSlotMapper {

    public static CustomerSlot toCustomerSlot(ParkCreateDto dto){
       return new ModelMapper().map(dto, CustomerSlot.class);
    }

    public ParkResponseDto toDto(CustomerSlot customerSlot){
        return new ModelMapper().map(customerSlot, ParkResponseDto.class);
    }
}
