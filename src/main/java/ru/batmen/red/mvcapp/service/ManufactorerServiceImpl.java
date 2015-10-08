package ru.batmen.red.mvcapp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.batmen.red.mvcapp.model.BannerImage;
import ru.batmen.red.mvcapp.model.Manufactorer;

import java.io.File;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.batmen.red.mvcapp.dao.ManufactorerDaoImpl;

@Service
public class ManufactorerServiceImpl implements ManufactorerService {

    public void setManufactorerDaoImpl(ManufactorerDaoImpl manufactorerDaoImpl) {
        this.manufactorerDaoImpl = manufactorerDaoImpl;
    }

    private ManufactorerDaoImpl manufactorerDaoImpl;

    @Override
    @Transactional
    public void addManufactorer(Manufactorer manufactorer) {
        if (manufactorer.getCreatedAt()==null){
            manufactorer.setCreatedAt(new Date());
        }
        if (manufactorer.getUpdatedAt()==null){
            manufactorer.setUpdatedAt(new Date());
        }
        manufactorerDaoImpl.addManufactorer(manufactorer);
    }

    @Override
    @Transactional
    public void updateManufactorer(Manufactorer manufactorer) {
        if (manufactorer.getCreatedAt()==null){
            manufactorer.setCreatedAt(new Date());
        }
        if (manufactorer.getUpdatedAt()==null){
            manufactorer.setUpdatedAt(new Date());
        }
        manufactorerDaoImpl.updateManufactorer(manufactorer);
    }

    @Override
    @Transactional
    public List<Manufactorer> listManufactorers() {
        return manufactorerDaoImpl.listManufactorer();
    }

    @Override
    @Transactional
    public void removeManufactorer(long id) {
        Manufactorer manufactorer = manufactorerDaoImpl.getManufactorer(id);
        manufactorerDaoImpl.removeManufactorer(id);
        File file = new File(Manufactorer.MANUFACTORER_IMAGES_DIR + manufactorer.getImagepath());
        if (file.exists()){
            file.delete();
        }
    }

    @Override
    @Transactional
    public Manufactorer getManufactorer(long id) {
        return manufactorerDaoImpl.getManufactorer(id);
    }

    @Override
    @Transactional
    public BigInteger getLastInsertedId() {
        return manufactorerDaoImpl.getLastInsertedId();
    }

    @Override
    @Transactional
    public void disableExistsManufactorers(){
        manufactorerDaoImpl.disableExistsManufactorers();
    }

    @Override
    @Transactional
    public void renewalManufactorers(Map<String, Manufactorer> mapNewManufactorers){

        //сделать всех стрых неактивными
        this.disableExistsManufactorers();

        //найти всех старых
        Map <String, Manufactorer> existsManufactorer = new HashMap<String, Manufactorer>();
        List<Manufactorer> listManufactorers = this.listManufactorers();

        for(Manufactorer manufactorer : listManufactorers){
            existsManufactorer.put(manufactorer.getTitle(), manufactorer);
        }

        Manufactorer currentManufactorer;

        for(Map.Entry<String, Manufactorer> entry: mapNewManufactorers.entrySet()){

            if (existsManufactorer.containsKey(entry.getKey())){
                currentManufactorer = existsManufactorer.get(entry.getKey());
                currentManufactorer.setState(Manufactorer.STATE_ACTIVE);
                this.updateManufactorer(currentManufactorer);
                mapNewManufactorers.put(entry.getKey(), currentManufactorer);
            }else{
                //добавим в бд
                currentManufactorer = mapNewManufactorers.get(entry.getKey());
                this.addManufactorer(currentManufactorer);
                //получим и обновим id
                currentManufactorer.setId((this.getLastInsertedId()).longValue());
                mapNewManufactorers.put(currentManufactorer.getTitle(), currentManufactorer);
            }

        }

    }
}
