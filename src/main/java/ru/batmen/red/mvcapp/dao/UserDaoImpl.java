package ru.batmen.red.mvcapp.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.batmen.red.mvcapp.model.User;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }

    @Override
    public void addUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(user);
    }

    @Override
    public void updateUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.update(user);
    }

    @Override
    public List<User> getUsers(int offset, int limit){
        Session session = sessionFactory.getCurrentSession();

        List<User> usersList = session.createQuery("from User")
                .setFirstResult(offset)
                .setMaxResults(limit)
                .list();

        return usersList;
    }

    @Override
    public List<User> listUsers() {
        Session session = sessionFactory.getCurrentSession();
        List<User> userList = (List<User>) session.createQuery("from User").list();

        return userList;
    }

    @Override
    public void removeUser(long id) {
        Session session = sessionFactory.getCurrentSession();
        User user = (User) session.load(User.class, new Long(id));
        if (null != user){
            session.delete(user);
        }
    }

    @Override
    public User getUser(long id) {
        Session session = sessionFactory.getCurrentSession();
        User user = (User) session.get(User.class, new Long(id));
        return user;
    }

    @Override
    public BigInteger getLastInsertedId() {
        Session session = sessionFactory.getCurrentSession();
        BigInteger lastId = (BigInteger) session.createSQLQuery("SELECT currval(pg_get_serial_sequence('users', 'id'));").uniqueResult();
        return lastId;
    }

    @SuppressWarnings("unchecked")
    public User findByUserName(String email) {

        List<User> users = new ArrayList<User>();

        users = sessionFactory.getCurrentSession()
                .createQuery("from User where email=?")
                .setParameter(0, email)
                .list();

        if (users.size() > 0) {
            return users.get(0);
        } else {
            return null;
        }

    }

}
