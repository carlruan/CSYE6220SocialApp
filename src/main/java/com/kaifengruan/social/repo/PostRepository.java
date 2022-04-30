package com.kaifengruan.social.repo;

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
public class PostRepository {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    @Transactional
    public Post save(Post post){
        Session session = this.sessionFactory.getCurrentSession();
        session.save(post);
        return post;
    }

    public List<Post> findAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Post> postList = session.createQuery("from Post").list();
        return postList;
    }

    public List<Post> findAllByUser(User user) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query=session.createQuery("FROM Post where user=:user");
        query.setParameter("user",user);
        List<Post> list=query.list();
        return list;
    }

    public Post findByPostId(String postId) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query=session.createQuery("FROM Post where postId=:postId");
        query.setParameter("postId",postId);
        List list=query.list();
        if(list.size() == 0) return null;
        return (Post)list.get(0);
    }


    public void delete(Post post) {
        Session session = this.sessionFactory.getCurrentSession();
        session.delete(post);
    }


}
