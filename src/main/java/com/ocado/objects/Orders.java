package com.ocado.objects;

import lombok.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Orders {

    private String orderId;
    private BigDecimal orderValue;
    private Duration duration;
    private LocalTime completeBy;

}
