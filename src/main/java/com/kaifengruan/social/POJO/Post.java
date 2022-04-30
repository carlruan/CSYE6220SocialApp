package com.kaifengruan.social.POJO;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="posts")
@EntityListeners(AuditingEntityListener.class)
public class Post{

    @Id
    @Column(name = "post_id")
    private String postId = UUID.randomUUID().toString();

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @OneToMany(mappedBy="post")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy="post")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Comment> comments = new ArrayList<>();

    @NotBlank
    private String content;
    private Date post_created;
    private Date post_updated;

    public void setPost_created(Date post_created) {
        this.post_created = post_created;
    }

    public void setPost_updated(Date post_updated) {
        this.post_updated = post_updated;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public Date getPost_created() {
        return post_created;
    }

    public Date getPost_updated() {
        return post_updated;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Post{" +
                "postId='" + postId + '\'' +

                ", content='" + content + '\'' +
                ", post_created=" + post_created +
                ", post_updated=" + post_updated +
                '}';
    }
}
