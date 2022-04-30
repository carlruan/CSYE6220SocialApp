package com.kaifengruan.social.repo;

import com.kaifengruan.social.POJO.Connect;
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
public class ConnectRepository {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    @Transactional
    public Connect save(Connect connect){
        Session session = this.sessionFactory.getCurrentSession();
        session.save(connect);
        return connect;
    }

    public List<Connect> findAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Connect> postList = session.createQuery("from Connect ").list();
        return postList;
    }

    public List<Connect> findAllByUser(User user) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query=session.createQuery("FROM Connect where user=:user");
        query.setParameter("user",user);
        List<Connect> list=query.list();
        return list;
    }

    public Connect findByConnectorIdAndUser(String connectId, User user) {
        Session session = this.sessionFactory.getCurrentSession();
        Query query= session.createQuery("from Connect where connectorId=:connectId and user=:user");
        query.setParameter("connectId",connectId);
        query.setParameter("user",user);
        List<Connect> list=query.list();
        return list.get(0);
    }


    public void delete(long connectId) {
        Session session = this.sessionFactory.getCurrentSession();
        Connect connect = (Connect) session.load(Connect.class, connectId);
        if (null != connect) {
            session.delete(connect);
        }
    }


}
