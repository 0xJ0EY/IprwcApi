package ipwrc.services;

import ipwrc.models.Subcategory;
import ipwrc.persistence.DAO;

import javax.inject.Inject;
import java.util.List;

public class SubcategoryService extends BaseService<Subcategory> {

    @Inject
    public SubcategoryService(DAO<Subcategory> dao) {
        super(dao);
    }

    public List<Subcategory> getAll() {
        return this.dao.getAll();
    }

    public Subcategory findByTitle(String title) {
        Subcategory subcategory = this.dao.getByTitle(title);

        // Check if it has a result, else throw an exception
        this.requireResult(subcategory);

        return subcategory;
    }

}
