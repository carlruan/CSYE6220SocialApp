package com.kaifengruan.social.dao;

import com.kaifengruan.social.POJO.Like;
import com.kaifengruan.social.POJO.Post;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import javax.transaction.Transactional;


@Transactional
@Component
public class LikeDao extends DAO{

    public Like save(Like like){
        begin();
        getSession().save(like);
        commit();
        return like;
    }

    public Like findByUsernameAndPost(String username, Post post){
        begin();
        Query query=getSession().createQuery("FROM Like where username=:username and post=:post");
        query.setParameter("username",username);
        query.setParameter("post",post);
        Like like = (Like)query.uniqueResult();
        commit();
        return like;
    }

    public void delete(long likeId){
        begin();
        Like like = (Like) session.load(Like.class, likeId);
        if (null != like) {
            getSession().delete(like);
        }
        commit();
    }

    public Like findById(long lid) {
        begin();
        Query query=getSession().createQuery("FROM Like where id=:lid");
        query.setParameter("lid",lid);
        Like like = (Like)query.uniqueResult();
        commit();
        return like;
    }

}
