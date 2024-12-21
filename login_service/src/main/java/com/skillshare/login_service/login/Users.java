package com.skillshare.login_service.login;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import jakarta.persistence.PrePersist;

@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Email(message = "Invalid email address")
    @NotNull(message = "Email cannot be null")
    private String email;

//    @Column(unique = true)
//    @Email(message = "Invalid email address")
//    @NotNull(message = "Email cannot be null")
//    @Lob
//    @Basic(fetch = FetchType.LAZY)
    private String publicKey;

    private LocalDateTime created_date;

    private LocalDateTime last_login;

    @PrePersist
    protected void onCreate() {
        this.created_date = LocalDateTime.now();
    }

    public LocalDateTime getCreated_date() {
        return created_date;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getLast_login() {
        return last_login;
    }

    public void setLast_login() {
        this.last_login = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);;
    }

    public Users(String email) {
        this.email = email;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public Users() {}
}
