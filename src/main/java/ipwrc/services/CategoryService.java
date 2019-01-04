package ipwrc.services;

import ipwrc.models.Category;
import ipwrc.persistence.CategoryDAO;

import javax.inject.Inject;
import java.util.List;

public class CategoryService extends BaseService<Category> {

    private CategoryDAO dao;

    @Inject
    public CategoryService(CategoryDAO dao) {
        this.dao = dao;
    }

    public List<Category> getAll() {
        return this.dao.getAll();
    }

    public Category findByTitle(String title) {
        Category category = this.dao.findByTitle(title).get();

        // Check if it has a result, else throw an exception
        this.requireResult(category);

        return category;
    }

}
