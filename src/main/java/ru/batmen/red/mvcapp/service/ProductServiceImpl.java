package ru.batmen.red.mvcapp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.batmen.red.mvcapp.dao.ProductDaoImpl;
import ru.batmen.red.mvcapp.model.BannerImage;
import ru.batmen.red.mvcapp.model.Manufactorer;
import ru.batmen.red.mvcapp.model.Product;

import java.io.File;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {
    public void setProductDaoImpl(ProductDaoImpl productDaoImpl) {
        this.productDaoImpl = productDaoImpl;
    }

    private ProductDaoImpl productDaoImpl;

    @Override
    @Transactional
    public void addProduct(Product product) {
        if (product.getCreatedAt()==null){
            product.setCreatedAt(new Date());
        }
        if (product.getUpdatedAt()==null){
            product.setUpdatedAt(new Date());
        }
        productDaoImpl.addProduct(product);
    }

    @Override
    @Transactional
    public void updateProduct(Product product) {
        if (product.getCreatedAt()==null){
            product.setCreatedAt(new Date());
        }
        if (product.getUpdatedAt()==null){
            product.setUpdatedAt(new Date());
        }
        productDaoImpl.updateProduct(product);
    }

    @Override
    @Transactional
    public List<Product> listProducts() {
        return productDaoImpl.listProducts();
    }

    @Override
    @Transactional
    public void removeProduct(long id) {
        Product product = productDaoImpl.getProduct(id);
        productDaoImpl.removeProduct(id);
        File file = new File(Product.PRODUCT_IMAGES_DIR + product.getImagepath());
        if (file.exists()){
            file.delete();
        }
    }

    @Override
    @Transactional
    public Product getProduct(long id) {
        return productDaoImpl.getProduct(id);
    }

    @Override
    @Transactional
    public BigInteger getLastInsertedId() {
        return productDaoImpl.getLastInsertedId();
    }

    @Override
    @Transactional
    public List<Product> getProducts(int type, int offset, int limit){
        return productDaoImpl.getProducts(type, offset, limit);
    }

    @Override
    @Transactional
    public void disableExistsProducts(){
        productDaoImpl.disableExistsProducts();
    }

    @Override
    @Transactional
    public void renewalProducts(Map<String, Product> mapNewProducts, Map<String, Manufactorer> mapManufactorer){
        //сделать всех стрых неактивными
        this.disableExistsProducts();

        //найти всех старых
        Map <String, Product> existsProducts = new HashMap<String, Product>();
        List<Product> listProducts = this.listProducts();

        for(Product product : listProducts){
            existsProducts.put(product.getNomencloture(), product);
        }

        Product currentProduct;
        int counter = 0;

        try {
            for (Map.Entry<String, Product> entry : mapNewProducts.entrySet()) {
                counter++;
                if (counter == 393) {
                    System.out.println("wqw");
                }
                if (existsProducts.containsKey(entry.getKey())) {
                    currentProduct = existsProducts.get(entry.getKey());

                    //возьмем производителя
                    String manufactorerTitle = currentProduct.getManufactorer().getTitle();
                    if (mapManufactorer.containsKey(manufactorerTitle)) {
                        currentProduct.setManufactorer(mapManufactorer.get(manufactorerTitle));
                        currentProduct.setState(Product.STATE_ACTIVE);
                        this.updateProduct(currentProduct);
                    }
                } else {
                    //добавим в бд
                    currentProduct = mapNewProducts.get(entry.getKey());

                    //возьмем производителя
                    String manufactorerTitle = currentProduct.getManufactorerName();
                    if (mapManufactorer.containsKey(manufactorerTitle)) {
                        //проверить производителя на активность

                        Manufactorer man = mapManufactorer.get(manufactorerTitle);

                        currentProduct.setManufactorer(mapManufactorer.get(manufactorerTitle));
                        currentProduct.setState(Product.STATE_ACTIVE);

                        this.addProduct(currentProduct);

                        //получим и обновим id
                        currentProduct.setId((this.getLastInsertedId()).longValue());
                        mapNewProducts.put(currentProduct.getNomencloture(), currentProduct);
                    }else{
                        //нет производителя
                    }
                }

            }
        }catch (Exception e){
            System.out.println(e.getStackTrace());
        }

    }
}
