package com.kaifengruan.social.POJO;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Entity
@Table(name="admin")
@EntityListeners(AuditingEntityListener.class)
public class Admin {
    @Id
    @Column(name = "user_id")
    private String userId = UUID.randomUUID().toString();

    @NotBlank
    private String email;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
