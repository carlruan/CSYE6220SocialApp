package com.kaifengruan.social.repo;

import com.kaifengruan.social.POJO.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class UserRepository {
    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    public User save(User user){
        Session session = this.sessionFactory.getCurrentSession();
        session.save(user);
        return user;
    }

    public List<User> getAllUsers() {
        Session session = this.sessionFactory.getCurrentSession();
        List<User> userList = session.createQuery("from User").list();
        return userList;
    }

    public User findByEmail(String email) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query=session.createQuery("FROM User where email=:email");
        query.setParameter("email",email);
        List list=query.list();
        if(list.size() == 0) return null;
        return (User)list.get(0);
    }

    public User findByUserId(String userId) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query=session.createQuery("FROM User where userId=:userId");
        query.setParameter("userId",userId);
        List list=query.list();
        if(list.size() == 0) return null;
        return (User)list.get(0);
    }

    public User findByUsername(String username) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query=session.createQuery("FROM User where username=:username");
        query.setParameter("username",username);
        List list=query.list();
        if(list.size() == 0) return null;
        return (User)list.get(0);
    }

    public void updateUser(User updateUser,String id) {
        Session session = this.sessionFactory.getCurrentSession();
        User user = (User) session.get(User.class, id);
        if(user!=null){
            user.setUsername(updateUser.getUsername());
            user.setEmail(updateUser.getEmail());
            user.setUserId(updateUser.getUserId());
            user.setFollows(updateUser.getFollows());
            user.setPosts(updateUser.getPosts());
            session.update(user);
        }
    }


    public void deleteUser(long id) {
        Session session = this.sessionFactory.getCurrentSession();
        User user = (User) session.load(User.class, id);
        if (null != user) {
            session.delete(user);
        }
    }

}
