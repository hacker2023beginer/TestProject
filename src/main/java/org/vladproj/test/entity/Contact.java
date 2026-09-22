package org.vladproj.test.entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import org.vladproj.test.annotation.ValidPhoneNumber;

@Embeddable
@Getter
@Setter
public class Contact {
    @ValidPhoneNumber
    private String phone;

    @Email
    private String email;
}
