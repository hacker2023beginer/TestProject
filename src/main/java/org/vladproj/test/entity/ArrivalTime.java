package org.vladproj.test.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Embeddable
@Getter
@Setter
public class ArrivalTime {
    private LocalTime checkIn;
    private LocalTime checkOut;
}