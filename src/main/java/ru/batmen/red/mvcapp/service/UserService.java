package ru.batmen.red.mvcapp.service;

import ru.batmen.red.mvcapp.model.User;

import java.math.BigInteger;
import java.util.List;

public interface UserService {
    public void addUser(User model);

    public void updateUser(User user);

    public List<User> listUsers();

    public void removeUser(long id);

    public User getUser(long id);

    public BigInteger getLastInsertedId();

    public List<User> getUsers(int offset, int limit);

}
