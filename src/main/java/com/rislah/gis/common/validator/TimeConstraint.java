package com.rislah.gis.common.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = TimeValidator.class)
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface TimeConstraint {
    String message() default "invalid time";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
