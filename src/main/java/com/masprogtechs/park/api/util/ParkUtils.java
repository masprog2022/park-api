package com.masprogtechs.park.api.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParkUtils {


    private static final double FIRST_0_MINUTES = 0.00;
    private static final double FIRST_60_MINUTES = 300.00;

    private static final double ADDITIONAL_15_MINUTES = 1.75;
    private static final double PERCENTAGE_DISCOUNT = 0.30;

    public static BigDecimal calculateCost(LocalDateTime entry, LocalDateTime output) { // calcular custo do estacionamento
        long minutes = entry.until(output, ChronoUnit.MINUTES); // pegar a quantidade de tempo entre 2 objectos
        double total = 0.0;

        if (minutes <= 10) {
            total = FIRST_0_MINUTES;
        } else if (minutes <= 60) {
            total = FIRST_60_MINUTES;
        } else {
            long additionalMinutes = (minutes - 60) / 60;
            total = 300.00 + (100.00 * additionalMinutes);
        }

        return new BigDecimal(total).setScale(2, RoundingMode.HALF_EVEN); // define a scala 2 caisa decimais
    }


    public static BigDecimal calculateDiscount(BigDecimal cost, long number_of_times) { // calcular desconto do parque de estacionamento
        BigDecimal discount = ((number_of_times > 0) && (number_of_times % 10 == 0))
                ? cost.multiply(new BigDecimal(PERCENTAGE_DISCOUNT))
                : new BigDecimal(0);
        return discount.setScale(2, RoundingMode.HALF_EVEN);
    }


    // 2023-09-23 12:36:50.959246
    public static String generateReceipt(){ // metodo gerar recibo
        LocalDateTime date = LocalDateTime.now();
        String receipt = date.toString().substring(0,19);
        return receipt.replace("-","")
                .replace(":", "")
                .replace("T", "-");
    }
}
