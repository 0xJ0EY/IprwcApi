package ipwrc.services;

import ipwrc.models.Category;
import ipwrc.persistence.DAO;

import javax.inject.Inject;
import java.util.List;

public class CategoryService extends BaseService<Category> {

    @Inject
    public CategoryService(DAO<Category> dao) {
        super(dao);
    }

    public List<Category> getAll() {
        return this.dao.getAll();
    }

    public Category findByTitle(String title) {
        Category category = this.dao.getByTitle(title);

        // Check if it has a result, else throw an exception
        this.requireResult(category);

        return category;
    }

}
