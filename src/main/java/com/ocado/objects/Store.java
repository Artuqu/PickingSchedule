package com.ocado.objects;

import lombok.*;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Store {

    private List<String> pickers;

    private LocalTime pickingStartTime;

    private LocalTime pickingEndTime;
}
