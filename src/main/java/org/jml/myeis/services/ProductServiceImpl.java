package org.jml.myeis.services;

import org.jml.myeis.domain.Customer;
import org.jml.myeis.domain.DomainObject;
import org.jml.myeis.domain.Product;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by jeremiahlumontod on 4/22/17.
 */

@Service
@Profile("map")
public class ProductServiceImpl extends AbstractMapService implements ProductService {


    @Override
    public List<DomainObject> listAll() {
        return super.listAll();
    }

    @Override
    public Product getById(Integer id) {
        Product product;
        product = (Product) super.getById(id);
        return product;
    }

    @Override
    public Product saveOrUpdate(Product product) {
        if(product!=null) {
            System.out.println("product object not null");
            if(product.getId()==null) {
                product.setId(getNextKey());
            }
            domainMap.put(product.getId(),product);
            return product;
        }else{
            System.out.println("product object null");
            return null;
        }
    }

    @Override
    public void delete(Integer id) {
        super.delete(id);
    }


    protected void loadDomainObjects() {

        domainMap = new HashMap<>();
        Product product1 = new Product();
        product1.setId(1);
        product1.setDescription("Product 1");
        product1.setPrice(new BigDecimal(12.99));
        product1.setImgUrl("http://products/product1.jpg");
        domainMap.put(1,product1);

        Product product2 = new Product();
        product2.setId(2);
        product2.setDescription("Product 2");
        product2.setPrice(new BigDecimal(12.99));
        product2.setImgUrl("http://products/product2.jpg");
        domainMap.put(2,product2);

        Product product3 = new Product();
        product3.setId(3);
        product3.setDescription("Product 3");
        product3.setPrice(new BigDecimal(12.99));
        product3.setImgUrl("http://products/product3.jpg");
        domainMap.put(3,product3);

        Product product4 = new Product();
        product4.setId(4);
        product4.setDescription("Product 4");
        product4.setPrice(new BigDecimal(12.99));
        product4.setImgUrl("http://products/product4.jpg");
        domainMap.put(4,product4);

        Product product5 = new Product();
        product5.setId(5);
        product5.setDescription("Product 5");
        product5.setPrice(new BigDecimal(12.99));
        product5.setImgUrl("http://products/product5.jpg");
        domainMap.put(5,product5);

    }

    public ProductServiceImpl() {
        loadDomainObjects();
    }

    private Integer getNextKey() {
        if(domainMap==null) {
            return 1;
        }
        if(domainMap.size()<1) {
            return 1;
        }
        return Collections.max(domainMap.keySet()) + 1;
    }

}
