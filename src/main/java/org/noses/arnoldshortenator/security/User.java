package org.noses.arnoldshortenator.security;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class User {

    @Id
    String email;

    @Enumerated(EnumType.STRING)
    private Provider provider;

}
