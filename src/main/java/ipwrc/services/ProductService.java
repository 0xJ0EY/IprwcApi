package ipwrc.services;

import ipwrc.models.Product;
import ipwrc.persistence.DAO;

import javax.inject.Inject;
import java.util.List;

public class ProductService extends BaseService<Product> {

    @Inject
    public ProductService(DAO<Product> dao) {
        super(dao);
    }

    public List<Product> getAll() {
        return this.dao.getAll();
    }

    public Product findById(int id) {
        Product product = this.dao.getById(id);

        // Check if it has a result, else throw an exception
        this.requireResult(product);

        return product;
    }

    public Product findByTitle(String title) {
        Product product = this.dao.getByTitle(title);

        // Check if it has a result, else throw an exception
        this.requireResult(product);

        System.out.println("product = " + product);

        return product;
    }

    public void create(Product product) {
        this.dao.create(product);
    }

}
