package com.kaifengruan.social.POJO;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="post_id", nullable=false)
    private Post post;

    @Column(name = "user_name")
    private String username;

    private Date post_created = new Date();


    public Date getPost_created() {
        return post_created;
    }

    public void setPost_created(Date post_created) {
        this.post_created = post_created;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Like{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}
