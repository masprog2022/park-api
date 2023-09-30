package com.masprogtechs.park.api.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParkUtils {

    // 2023-09-23 12:36:50.959246

    public static String generateReceipt(){ // metodo gerar recibo
        LocalDateTime date = LocalDateTime.now();
        String receipt = date.toString().substring(0,19);
        return receipt.replace("-","")
                .replace(":", "")
                .replace("T", "-");
    }
}
