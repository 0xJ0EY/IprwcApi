package ipwrc.services;

import ipwrc.models.Product;
import ipwrc.models.ProductImage;
import ipwrc.persistence.DAO;

import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

public class ProductService extends BaseService<Product> {

    @Inject
    public ProductService(DAO<Product> dao) {
        super(dao);
    }

    @Override
    public void create(Product obj) {
        // Bind the parent object
        for (ProductImage image : obj.getImages()) {
            image.setProduct(obj);
        }

        super.create(obj);
    }

    @Override
    public void update(Product obj) {
        // Bind the parent object
        for (ProductImage image : obj.getImages()) {
            image.setProduct(obj);
        }

        super.update(obj);
    }

    public ProductImage getProductImage(Product obj, int index) {
        try {
            return obj.getImages().get(index);
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            throw new NotFoundException();
        }
    }

}
