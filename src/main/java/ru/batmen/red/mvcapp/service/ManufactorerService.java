package ru.batmen.red.mvcapp.service;

import ru.batmen.red.mvcapp.model.Manufactorer;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public interface ManufactorerService {

    public void addManufactorer(Manufactorer manufactorer);

    public void updateManufactorer(Manufactorer manufactorer);

    public List<Manufactorer> listManufactorers();

    public void removeManufactorer(long id);

    public Manufactorer getManufactorer(long id);

    public BigInteger getLastInsertedId();

    //выгрузка в базу
    public void renewalManufactorers(Map<String, Manufactorer> mapNewManufactorers);

    //дисаблим старых
    public void disableExistsManufactorers();

}

