package com.example.model.user;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.util.DigestUtils;
import java.security.SecureRandom;
import java.util.Base64;

@Data
@Entity
@Table(name = "user")
public class User {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    @Column(name = "password_hash", nullable = false)
    private String password;
    @Column(name = "salt", nullable = false)
    private String salt;

    @PrePersist
    protected void onCreate() {
        if (this.salt == null) {
            this.salt = generateSalt();
        }
        this.password = calculateHash(this.password, this.salt);
    }

    private String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[16];
        random.nextBytes(saltBytes);
        return Base64.getEncoder().encodeToString(saltBytes);
    }

    private String calculateHash(String password, String salt) {
        String combined = password + salt;
        byte[] hashBytes = DigestUtils.md5Digest(combined.getBytes());
        return DigestUtils.md5DigestAsHex(hashBytes);
    }

}
