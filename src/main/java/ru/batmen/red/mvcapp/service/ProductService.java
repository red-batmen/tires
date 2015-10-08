package ru.batmen.red.mvcapp.service;

import ru.batmen.red.mvcapp.model.Manufactorer;
import ru.batmen.red.mvcapp.model.Product;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public interface ProductService {
    public void addProduct(Product product);

    public void updateProduct(Product product);

    public List<Product> listProducts();

    public void removeProduct(long id);

    public Product getProduct(long id);

    public BigInteger getLastInsertedId();

    public void renewalProducts(Map<String, Product> mapNewProducts, Map<String, Manufactorer> mapManufactorer);

    public List<Product> getProducts(int type, int offset, int limit);

    //дисаблим старых
    public void disableExistsProducts();

}
