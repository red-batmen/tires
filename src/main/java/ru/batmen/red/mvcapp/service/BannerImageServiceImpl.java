package ru.batmen.red.mvcapp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.batmen.red.mvcapp.dao.BannerImageDaoImpl;
import ru.batmen.red.mvcapp.model.BannerImage;

import java.io.File;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Service
public class BannerImageServiceImpl implements BannerImageService {

    private BannerImageDaoImpl bannerImageDaoImpl;

    public void setBannerImageDaoImpl(BannerImageDaoImpl bannerImageDaoImpl) {
        this.bannerImageDaoImpl = bannerImageDaoImpl;
    }

    @Override
    @Transactional
    public void addBannerImage(BannerImage bannerImage) {
        if (bannerImage.getCreatedAt()==null){
            bannerImage.setCreatedAt(new Date());
        }
        if (bannerImage.getUpdatedAt()==null){
            bannerImage.setUpdatedAt(new Date());
        }
        bannerImageDaoImpl.addBannerImage(bannerImage);
    }

    @Override
    @Transactional
    public void updateBannerImage(BannerImage bannerImage) {
        if (bannerImage.getCreatedAt()==null){
            bannerImage.setCreatedAt(new Date());
        }
        if (bannerImage.getUpdatedAt()==null){
            bannerImage.setUpdatedAt(new Date());
        }
        bannerImageDaoImpl.updateBannerImage(bannerImage);
    }

    @Override
    @Transactional
    public List<BannerImage> listBannerImage() {
        return bannerImageDaoImpl.listBannerImage();
    }

    @Override
    @Transactional
    public void removeBannerImage(long id) {
        BannerImage image = bannerImageDaoImpl.getBannerImage(id);
        bannerImageDaoImpl.removeBannerImage(id);
        File file = new File(BannerImage.BANNER_IMAGES_DIR + image.getImagepath());
        if (file.exists()){
            file.delete();
        }
    }

    @Override
    @Transactional
    public BannerImage getBannerImage(long id) {
        return bannerImageDaoImpl.getBannerImage(id);
    }

    @Override
    @Transactional
    public BigInteger getLastInsertedId() {
        return bannerImageDaoImpl.getLastInsertedId();
    }
}
