package com.taklip.yoda.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.taklip.yoda.model.User;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, User> {
    @Override
    public void initialize(PasswordMatch constraintAnnotation) {
    }
    
    @Override
    public boolean isValid(User user, ConstraintValidatorContext context) {
        if (user.getPassword() == null || user.getVerifyPassword() == null) {
            return true;
        }
        return user.getPassword().equals(user.getVerifyPassword());
    }
}