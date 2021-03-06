package ru.batmen.red.mvcapp.service;

import ru.batmen.red.mvcapp.model.BannerImage;

import java.math.BigInteger;
import java.util.List;

public interface BannerImageService {

    public void addBannerImage(BannerImage bannerImage);
    public void updateBannerImage(BannerImage bannerImage);
    public List<BannerImage> listBannerImage();
    public void removeBannerImage(long id);
    public BannerImage getBannerImage(long id);
    public BigInteger getLastInsertedId();

}

