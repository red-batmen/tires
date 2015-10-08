package ru.batmen.red.mvcapp.dao;

import ru.batmen.red.mvcapp.model.Manufactorer;

import java.math.BigInteger;
import java.util.List;

public interface ManufactoreDao {

    public void addManufactorer(Manufactorer manufactorer);
    public void updateManufactorer(Manufactorer manufactorer);
    public List<Manufactorer> listManufactorer();
    public void removeManufactorer(long id);
    public Manufactorer getManufactorer(long id);
    public BigInteger getLastInsertedId();
    public void disableExistsManufactorers();
}
