package com.example.mongodbservice.models;

import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@NoArgsConstructor
public class MapContainer {

    private Map<Integer, LocalDateTime> map;

    public Map<Integer, LocalDateTime> getMap() {
        map = Map.of(0, LocalDateTime.now().plus(Duration.of(5, ChronoUnit.MINUTES)),
                1, LocalDateTime.now().plus(Duration.of(1, ChronoUnit.HOURS)),
                2, LocalDateTime.now().plus(Duration.of(1, ChronoUnit.DAYS)),
                3, LocalDateTime.now().plus(Duration.of(3, ChronoUnit.DAYS)),
                4, LocalDateTime.now().plus(Duration.of(1, ChronoUnit.WEEKS)),
                5, LocalDateTime.now().plus(Duration.of(1, ChronoUnit.MONTHS)));
        return map;
    }


}