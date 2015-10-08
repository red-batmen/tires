package ru.batmen.red.mvcapp.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.batmen.red.mvcapp.model.BannerImage;

import java.math.BigInteger;
import java.util.List;

@Repository
public class BannerImageDaoImpl implements BannerImageDao {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addBannerImage(BannerImage bannerImage) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(bannerImage);
    }

    @Override
    public void updateBannerImage(BannerImage bannerImage) {
        Session session = sessionFactory.getCurrentSession();
        session.update(bannerImage);
    }

    @Override
    public List<BannerImage> listBannerImage() {
        Session session = sessionFactory.getCurrentSession();
        List<BannerImage> bannerImageList = (List<BannerImage>) session.createQuery("from BannerImage").list();

        return bannerImageList;
    }

    @Override
    public void removeBannerImage(long id) {
        Session session = sessionFactory.getCurrentSession();
        BannerImage bannerImage = (BannerImage) session.load(BannerImage.class, new Long(id));
        if (null != bannerImage){
            session.delete(bannerImage);
        }
    }

    @Override
    public BannerImage getBannerImage(long id) {
        Session session = sessionFactory.getCurrentSession();
        //Manufactorer manufactorer = (Manufactorer) session.load(Manufactorer.class, new Long(id));
        BannerImage bannerImage = (BannerImage) session.get(BannerImage.class, new Long(id));
        return bannerImage;
    }

    @Override
    public BigInteger getLastInsertedId() {
        Session session = sessionFactory.getCurrentSession();
        BigInteger lastId = (BigInteger) session.createSQLQuery("SELECT currval(pg_get_serial_sequence('banner_images', 'id'));").uniqueResult();
        return lastId;
    }

}
