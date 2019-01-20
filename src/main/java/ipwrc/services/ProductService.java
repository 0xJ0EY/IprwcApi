package ipwrc.services;

import ipwrc.models.Product;
import ipwrc.persistence.ProductDAO;

public class ProductService extends BaseService<Product> {

    private ProductDAO dao;

    public ProductService(ProductDAO dao) {
        this.dao = dao;
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

        return product;
    }

    public void create(Product product) {
        this.dao.create(product);
    }

}
