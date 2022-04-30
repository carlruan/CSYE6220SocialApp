package com.kaifengruan.social.dao;

import com.kaifengruan.social.POJO.Comment;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Transactional
@Component
public class CommentDao extends DAO{

    public Comment save(Comment comment){
        begin();
        getSession().save(comment);
        commit();
        return comment;
    }

    public void delete(long commentId) {
        begin();
        Comment comment = (Comment) getSession().load(Comment.class, commentId);
        if (null != comment) {
            getSession().delete(comment);
        }
        commit();
    }

    public Comment findById(long cid) {
        begin();
        Query query=getSession().createQuery("FROM Comment where id=:cid");
        query.setParameter("cid",cid);
        Comment comment = (Comment)query.uniqueResult();
        commit();
        return comment;
    }

}
