package ru.batmen.red.mvcapp.dao;

import ru.batmen.red.mvcapp.model.Product;
import ru.batmen.red.mvcapp.model.User;

import java.math.BigInteger;
import java.util.List;

public interface UserDao {

    public void addUser(User user);
    public void updateUser(User user);
    public List<User> listUsers();
    public List<User> getUsers(int offset, int limit);
    public void removeUser(long id);
    public User getUser(long id);
    public BigInteger getLastInsertedId();

    public User findByUserName(String email);

}
