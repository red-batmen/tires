package ru.batmen.red.mvcapp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.batmen.red.mvcapp.dao.ProductDaoImpl;
import ru.batmen.red.mvcapp.dao.UserDaoImpl;
import ru.batmen.red.mvcapp.model.Manufactorer;
import ru.batmen.red.mvcapp.model.Product;
import ru.batmen.red.mvcapp.model.User;

import java.io.File;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    public void setUserDaoImpl(UserDaoImpl userDaoImpl) {
        this.userDaoImpl = userDaoImpl;
    }

    private UserDaoImpl userDaoImpl;

    @Override
    @Transactional
    public void addUser(User model) {
        if (model.getCreatedAt()==null){
            model.setCreatedAt(new Date());
        }
        if (model.getUpdatedAt()==null){
            model.setUpdatedAt(new Date());
        }
        userDaoImpl.addUser(model);
    }

    @Override
    @Transactional
    public void updateUser(User model) {
        if (model.getCreatedAt()==null){
            model.setCreatedAt(new Date());
        }
        if (model.getUpdatedAt()==null){
            model.setUpdatedAt(new Date());
        }
        userDaoImpl.updateUser(model);
    }

    @Override
    @Transactional
    public List<User> listUsers() {
        return userDaoImpl.listUsers();
    }

    @Override
    @Transactional
    public void removeUser(long id) {
        User model = userDaoImpl.getUser(id);
        userDaoImpl.removeUser(id);
    }

    @Override
    @Transactional
    public User getUser(long id) {
        return userDaoImpl.getUser(id);
    }

    @Override
    @Transactional
    public BigInteger getLastInsertedId() {
        return userDaoImpl.getLastInsertedId();
    }

    @Override
    @Transactional
    public List<User> getUsers(int offset, int limit){
        return userDaoImpl.getUsers(offset, limit);
    }

}
