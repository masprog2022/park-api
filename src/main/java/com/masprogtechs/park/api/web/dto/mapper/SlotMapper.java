package com.masprogtechs.park.api.web.dto.mapper;

import com.masprogtechs.park.api.entity.Slot;
import com.masprogtechs.park.api.web.dto.SlotCreateDto;
import com.masprogtechs.park.api.web.dto.SlotResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SlotMapper {

    public static Slot toSlot(SlotCreateDto dto){

        return new ModelMapper().map(dto, Slot.class);
    }

    public static SlotResponseDto toDto(Slot slot){
        return new ModelMapper().map(slot, SlotResponseDto.class);
    }
}
