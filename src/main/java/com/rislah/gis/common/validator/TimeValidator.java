package com.rislah.gis.common.validator;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class TimeValidator implements ConstraintValidator<TimeConstraint, Long> {
    @Override
    public void initialize(TimeConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return value > 1609462800;
    }
}
