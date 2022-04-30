package com.kaifengruan.social.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class DAO {
    Configuration cfg;
    SessionFactory sf;
    Session session;
    Transaction tx;
    DAO(){
        cfg = new Configuration();
        sf = cfg.configure("hibernate.cfg.xml").buildSessionFactory();
        session = sf.openSession();
        tx=null;
    }

    public void begin() {
        tx = session.beginTransaction();
    }

    public Session getSession() {
        return this.session;
    }
    protected void commit() {
        tx.commit();
    }

    public void close() {
        session.close();
    }
}
