package com.kaifengruan.social.dao;

import com.kaifengruan.social.POJO.Post;
import com.kaifengruan.social.POJO.User;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Component
public class PostDao extends DAO{
    public Post save(Post post){
        begin();
        getSession().save(post);
        commit();
        return post;
    }

    public List<Post> findAll() {
        begin();
        List<Post> postList = getSession().createQuery("from Post").list();
        commit();
        return postList;
    }

    public List<Post> findAllByUser(User user) {
        begin();
        Query query=getSession().createQuery("FROM Post where user=:user");
        query.setParameter("user",user);
        List<Post> list=query.list();
        commit();
        return list;
    }

    public Post findByPostId(String postId) {
        begin();
        Query query=getSession().createQuery("FROM Post where postId=:postId");
        query.setParameter("postId",postId);
        Post post = (Post)query.uniqueResult();
        commit();
        return post;
    }

    public void delete(Post post) {
        begin();
        getSession().delete(post);
        commit();
    }
}
