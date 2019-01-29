package ipwrc.services;

import ipwrc.models.Product;
import ipwrc.persistence.DAO;

import javax.inject.Inject;

public class ProductService extends BaseService<Product> {

    @Inject
    public ProductService(DAO<Product> dao) {
        super(dao);
    }

}
