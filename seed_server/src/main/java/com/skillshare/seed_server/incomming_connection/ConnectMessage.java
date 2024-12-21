package com.skillshare.seed_server.incomming_connection;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import jakarta.persistence.PrePersist;

@Entity
@Table(name = "Connections", uniqueConstraints = @UniqueConstraint(columnNames = "publicKey"))
public class ConnectMessage {

//    lot's of write so mongo db
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(name = "publicKey", unique = true, nullable = false)
    @NotNull(message = "publicKey cannot be null")
    private String publicKey;
    @NotNull(message = "have some sense give a greeting")
    private String greeting;

    @NotNull(message = "endpoint cannot be null")
    private String endPoint;
    private LocalDateTime lastActive;

    @PrePersist
    protected void onCreate() {
        this.lastActive = LocalDateTime.now();
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public void setLastActive(LocalDateTime lastActive) {
        this.lastActive = lastActive;
    }


    public LocalDateTime getLastActive() {
        return lastActive;
    }


    public ConnectMessage() {}


}
