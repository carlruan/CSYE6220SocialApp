package com.kaifengruan.social.POJO;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="users")
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @Column(name = "user_id")
    private String userId;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @NotBlank
    private String email;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @OneToMany(mappedBy="user")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy="user")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Connect> follows = new ArrayList<>();

    public List<Connect> getFollows() {
        return follows;
    }

    public void setFollows(List<Connect> follows) {
        this.follows = follows;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
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

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
