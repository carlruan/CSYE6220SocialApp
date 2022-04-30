package com.kaifengruan.social.dao;

import com.kaifengruan.social.POJO.Connect;
import com.kaifengruan.social.POJO.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Component
public class ConnectDao extends DAO{
    public Connect save(Connect connect){
        begin();
        getSession().save(connect);
        commit();
        return connect;
    }

    public List<Connect> findAll() {
        begin();
        List<Connect> postList = getSession().createQuery("from Connect ").list();
        commit();
        return postList;
    }

    public List<Connect> findAllByUser(User user) {
        begin();
        Query query=getSession().createQuery("FROM Connect where user=:user");
        query.setParameter("user",user);
        List<Connect> list=query.list();
        commit();
        return list;
    }

    public Connect findByConnectorIdAndUser(String connectId, User user) {
        begin();
        Query query= getSession().createQuery("from Connect where connectorId=:connectId and user=:user");
        query.setParameter("connectId",connectId);
        query.setParameter("user",user);
        Connect connect = (Connect)query.uniqueResult();
        commit();
        return connect;
    }


    public void delete(long connectId) {
        begin();
        Connect connect = (Connect) getSession().load(Connect.class, connectId);
        if (null != connect) {
            getSession().delete(connect);
        }
        commit();

    }

}
