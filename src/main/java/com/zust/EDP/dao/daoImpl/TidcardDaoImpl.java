package com.zust.EDP.dao.daoImpl;

import com.zust.EDP.dao.TidcardDao;
import com.zust.EDP.entity.Tidcard;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TidcardDaoImpl implements TidcardDao {
    @Autowired
    private SessionFactory sessionFactory;

    public Session getCurrentSession() {
        return this.sessionFactory.getCurrentSession();
    }


    @Override
    public void saveTidcard(Tidcard tidcard) {
        getCurrentSession().save(tidcard);
    }

    @Override
    public List<Tidcard> selectByNum(String cardnum) {
        String sql = "from Tidcard as idcard where idcard.idcardnum='" + cardnum + "'";
        return getCurrentSession().createQuery(sql).list();
    }
}
