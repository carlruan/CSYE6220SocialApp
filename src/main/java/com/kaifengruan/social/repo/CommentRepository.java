package com.kaifengruan.social.repo;

import com.kaifengruan.social.POJO.Comment;
import com.kaifengruan.social.POJO.Connect;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class CommentRepository {
    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    public Comment save(Comment comment){
        Session session = this.sessionFactory.getCurrentSession();
        session.save(comment);
        return comment;
    }

    public void delete(long commentId) {
        Session session = this.sessionFactory.getCurrentSession();
        Comment comment = (Comment) session.load(Comment.class, commentId);
        if (null != comment) {
            session.delete(comment);
        }
    }

}
