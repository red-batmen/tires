package ru.batmen.red.mvcapp.dao;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ru.batmen.red.mvcapp.model.Manufactorer;
import ru.batmen.red.mvcapp.model.Product;

import java.math.BigInteger;
import java.util.List;

@Repository
public class ProductDaoImpl implements ProductDao {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }

    @Override
    public void addProduct(Product product) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(product);
    }

    @Override
    public void updateProduct(Product product) {
        Session session = sessionFactory.getCurrentSession();
        session.update(product);
    }

    @Override
    public List<Product> getProducts(int type, int offset, int limit){
        Session session = sessionFactory.getCurrentSession();
        //List<Product> productsList = (List<Product>) session.createQuery("from Product").list();

        List<Product> productsList;

        if (type != Manufactorer.TYPE_DRIVE_AND_TIRE){
            productsList = session.createCriteria(Product.class)
                .createAlias("manufactorer", "m", Criteria.LEFT_JOIN, Restrictions.eq("m.type", new Integer(type)))
                .setMaxResults(limit)
                .setFirstResult(offset)
                .list();
        }else{
            productsList = session.createQuery("from Product")
                .setFirstResult(offset)
                .setMaxResults(limit)
                .list();
        }

        return productsList;
    }

    @Override
    public List<Product> listProducts() {
        Session session = sessionFactory.getCurrentSession();
        List<Product> productsList = (List<Product>) session.createQuery("from Product").list();

        return productsList;
    }

    @Override
    public void removeProduct(long id) {
        Session session = sessionFactory.getCurrentSession();
        Product product = (Product) session.load(Product.class, new Long(id));
        if (null != product){
            session.delete(product);
        }
    }

    @Override
    public Product getProduct(long id) {
        Session session = sessionFactory.getCurrentSession();
        Product product = (Product) session.get(Product.class, new Long(id));
        return product;
    }

    @Override
    public BigInteger getLastInsertedId() {
        Session session = sessionFactory.getCurrentSession();
        BigInteger lastId = (BigInteger) session.createSQLQuery("SELECT currval(pg_get_serial_sequence('products', 'id'));").uniqueResult();
        return lastId;
    }

    @Override
    public void disableExistsProducts() {
        Session session = sessionFactory.getCurrentSession();
        Query query  = session.createSQLQuery("UPDATE products set state = :state ");
        query.setParameter("state", Product.STATE_REMOVED);
        query.executeUpdate();
    }

}
