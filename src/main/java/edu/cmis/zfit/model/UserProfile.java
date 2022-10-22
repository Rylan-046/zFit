package edu.cmis.zfit.model;

import java.time.LocalDate;

public record UserProfile(
        String id,
        LocalDate birthDate,
        Gender gender,
        String firstName,
        String lastName) {
}