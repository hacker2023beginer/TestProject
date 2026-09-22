package org.vladproj.test.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Address {
    private String houseNumber;
    private String street;
    private String city;
    private String country;
    private String postCode;
}
