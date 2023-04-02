package com.ocado.objects;

import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Picker {

    private String picker;
    private String orderId;
    private LocalTime pickingStartTime;
    private LocalTime pickingEndTime;
}
