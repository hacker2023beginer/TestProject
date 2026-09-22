package org.vladproj.test.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.vladproj.test.annotation.ValidPhoneNumber;

public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {
    private static final String BLR_PHONE_REGEXP = "^\\+375 \\d{2} \\d{3}-\\d{2}-\\d{2}$";

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext constraintValidatorContext) {
        return phone.matches(BLR_PHONE_REGEXP);
    }
}
