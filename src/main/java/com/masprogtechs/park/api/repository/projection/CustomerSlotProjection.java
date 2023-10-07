package com.masprogtechs.park.api.repository.projection;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface CustomerSlotProjection {

     String getPlate();
     String getMake();
     String getModel();
     String getColor();
     String getCustomerCpf();
     String getReceipt();

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
     LocalDateTime getInputData();

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
     LocalDateTime getOutputData();


     String getSlotCode();
     BigDecimal getCharge();
     BigDecimal getDiscount();
}
