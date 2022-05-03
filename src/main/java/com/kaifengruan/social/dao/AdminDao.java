package com.kaifengruan.social.dao;

import com.kaifengruan.social.POJO.Admin;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Transactional
@Component
public class AdminDao extends DAO{
    public Admin save(Admin admin){
        begin();
        getSession().save(admin);
        commit();
        return admin;
    }

    public Admin findByEmailAndPassword(String email, String password){
        begin();
        Query query=getSession().createQuery("FROM Admin where email=:email and password=:password");
        query.setParameter("email",email);
        query.setParameter("password",password);
        Admin admin = (Admin)query.uniqueResult();
        commit();
        return admin;
    }

    public Admin findByEmail(String email){
        begin();
        Query query=getSession().createQuery("FROM Admin where email=:email");
        query.setParameter("email",email);
        Admin admin = (Admin)query.uniqueResult();
        commit();
        return admin;
    }

}
