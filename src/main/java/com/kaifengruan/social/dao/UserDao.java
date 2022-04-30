package com.kaifengruan.social.dao;

import com.kaifengruan.social.POJO.User;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public class UserDao extends DAO{

    public User findByEmail(String email){
        begin();
        Query q = getSession().createQuery("from User u where u.email = :email");
        q.setParameter("email", email);
        Object user = q.uniqueResult();
        commit();
        return (User)user;
    }

    public User findByUserId(String userId){
        begin();
        Query q = getSession().createQuery("from User u where u.userId = :userId");
        q.setParameter("userId", userId);
        User user = (User) q.uniqueResult();
        commit();
        return user;
    }

    public User findByUsername(String username){
        begin();
        Query q = getSession().createQuery("from User u where u.username = :username");
        q.setParameter("username", username);
        User user = (User) q.uniqueResult();
        commit();
        return user;
    }

    public List<User> getAllUsers(){
        begin();
        Query q = getSession().createQuery("from User");
        List<User> userList = q.list();
        commit();
        return userList;
    }

    public User save(User user){
        begin();
        getSession().save(user);
        commit();
        return user;
    }
}
