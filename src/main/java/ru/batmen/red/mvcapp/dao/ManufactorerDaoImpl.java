package ru.batmen.red.mvcapp.dao;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import ru.batmen.red.mvcapp.model.Manufactorer;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.math.BigInteger;
import java.util.List;

@Repository
public class ManufactorerDaoImpl implements ManufactoreDao {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addManufactorer(Manufactorer manufactorer) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(manufactorer);
    }

    @Override
    public void updateManufactorer(Manufactorer manufactorer) {
        Session session = sessionFactory.getCurrentSession();
        session.update(manufactorer);
    }

    @Override
    public List<Manufactorer> listManufactorer() {
        Session session = sessionFactory.getCurrentSession();
        List<Manufactorer> manufactorersList = (List<Manufactorer>) session.createQuery("from Manufactorer").list();

        return manufactorersList;
    }

    @Override
    public void removeManufactorer(long id) {
        Session session = sessionFactory.getCurrentSession();
        Manufactorer manufactorer = (Manufactorer) session.load(Manufactorer.class, new Long(id));
        if (null != manufactorer){
            session.delete(manufactorer);
        }
    }

    @Override
    public Manufactorer getManufactorer(long id) {
        Session session = sessionFactory.getCurrentSession();
        //Manufactorer manufactorer = (Manufactorer) session.load(Manufactorer.class, new Long(id));
        Manufactorer manufactorer = (Manufactorer) session.get(Manufactorer.class, new Long(id));
        return manufactorer;
    }

    @Override
    public BigInteger getLastInsertedId() {
        Session session = sessionFactory.getCurrentSession();
        BigInteger lastId = (BigInteger) session.createSQLQuery("SELECT currval(pg_get_serial_sequence('manufactorers', 'id'));").uniqueResult();
        return lastId;
    }

    @Override
    public void disableExistsManufactorers() {
        Session session = sessionFactory.getCurrentSession();
        Query query  = session.createQuery("UPDATE Manufactorer set state = :state ");
        query.setParameter("state", Manufactorer.STATE_REMOVED);
        query.executeUpdate();
    }
}
