package com.kaifengruan.social.repo;

import com.kaifengruan.social.POJO.Comment;
import com.kaifengruan.social.POJO.Like;
import com.kaifengruan.social.POJO.Post;
import com.kaifengruan.social.POJO.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class LikeRepository {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    public Like save(Like like){
        Session session = this.sessionFactory.getCurrentSession();
        session.save(like);
        return like;
    }

    public Like findByUsernameAndPost(String username, Post post){
        Session session = this.sessionFactory.getCurrentSession();
        Query query=session.createQuery("FROM Like where username=:username and post=:post");
        query.setParameter("username",username);
        query.setParameter("post",post);
        List list=query.list();
        if(list.size() == 0) return null;
        return (Like)list.get(0);
    }

    public void delete(long likeId){
        Session session = this.sessionFactory.getCurrentSession();
        Like like = (Like) session.load(Like.class, likeId);
        if (null != like) {
            session.delete(like);
        }
    }

}
