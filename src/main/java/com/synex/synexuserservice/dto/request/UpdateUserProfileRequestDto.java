package com.synex.synexuserservice.dto.request;

import com.synex.synexuserservice.entity.UserProfileEntity;
import com.synex.synexuserservice.enums.Gender;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UpdateUserProfileRequestDto(
        @Size(min = 3, message = "First Name must be at least 3 characters long.")
        String firstName,

        @Size(min = 3, message = "Last Name must be at least 3 characters long.")
        String lastName,

        @Size(min = 4, message = "Nickname must be at least 4 characters long.")
        String nickname,

        @Past(message = "The date of birth must be in the past.")
        LocalDate birthDate,

        Gender gender
) {

    public boolean isEmpty() {
        return firstName == null
                && lastName == null
                && nickname == null
                && birthDate == null
                && gender == null;
    }

    public UserProfileEntity updateEntity(UserProfileEntity entity) {
        if (firstName != null) {
            entity.setFirstName(firstName);
        }

        if (lastName != null) {
            entity.setLastName(lastName);
        }

        if (nickname != null) {
            entity.setNickname(nickname);
        }

        if (birthDate != null) {
            entity.setBirthDate(birthDate);
        }

        if (gender != null) {
            entity.setGender(gender);
        }

        return entity;
    }
}
