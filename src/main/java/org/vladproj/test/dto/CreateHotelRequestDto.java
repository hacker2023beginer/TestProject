package org.vladproj.test.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.vladproj.test.entity.Address;
import org.vladproj.test.entity.ArrivalTime;
import org.vladproj.test.entity.Contact;

@Getter
@Setter
public class CreateHotelRequestDto {
    @NotBlank
    private String name;

    private String description;

    @NotBlank
    private String brand;

    @Valid
    @NotNull
    private Address address;

    @Valid
    @NotNull
    private Contact contacts;

    @Valid
    private ArrivalTime arrivalTime;
}
