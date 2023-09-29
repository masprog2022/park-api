package com.masprogtechs.park.api.service;
import com.masprogtechs.park.api.exception.EntityRuntimeException;
import com.masprogtechs.park.api.entity.Slot;
import com.masprogtechs.park.api.exception.CodeUniqueViolationException;
import com.masprogtechs.park.api.repository.SlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class SlotService {

    private final SlotRepository slotRepository;

    @Transactional
    public Slot save(Slot slot){
        try {
            return slotRepository.save(slot);
        }catch (DataIntegrityViolationException ex){
            throw  new CodeUniqueViolationException(
                    String.format("Vaga com código %s já cadastrada", slot.getCode()));
        }
    }

    @Transactional(readOnly = true)
    public Slot findByCode(String code){
        return slotRepository.findByCode(code).orElseThrow(
                () -> new EntityRuntimeException(String.format("Vaga com o code %s não foi encontrado.", code))
        );
    }
}
