package com.kaifengruan.social.repo;

import com.kaifengruan.social.POJO.Admin;
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
public class AdminRepository {
    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    public Admin save(Admin admin){
        Session session = this.sessionFactory.getCurrentSession();
        session.save(admin);
        return admin;
    }

    public Admin findByEmailAndPassword(String email, String password){
        Session session = this.sessionFactory.getCurrentSession();
        Query query=session.createQuery("FROM Admin where email=:email and password=:password");
        query.setParameter("email",email);
        query.setParameter("password",password);
        List list=query.list();
        if(list.size() == 0) return null;
        return (Admin)list.get(0);
    }
}
