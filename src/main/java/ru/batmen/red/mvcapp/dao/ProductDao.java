package ru.batmen.red.mvcapp.dao;

import ru.batmen.red.mvcapp.model.Product;

import java.math.BigInteger;
import java.util.List;

public interface ProductDao {

    public void addProduct(Product product);
    public void updateProduct(Product product);
    public List<Product> listProducts();
    public List<Product> getProducts(int type, int offset, int limit);
    public void removeProduct(long id);
    public Product getProduct(long id);
    public BigInteger getLastInsertedId();
    public void disableExistsProducts();

}
