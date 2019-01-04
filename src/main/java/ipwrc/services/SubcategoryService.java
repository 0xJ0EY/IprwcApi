package ipwrc.services;

import ipwrc.models.Subcategory;
import ipwrc.persistence.SubcategoryDAO;

import javax.inject.Inject;
import java.util.List;

public class SubcategoryService extends BaseService<Subcategory> {

    private SubcategoryDAO dao;

    @Inject
    public SubcategoryService(SubcategoryDAO dao) {
        this.dao = dao;
    }

    public List<Subcategory> getAll() {
        return this.dao.getAll();
    }

    public Subcategory findByTitle(String title) {
        Subcategory subcategory = this.dao.findByTitle(title).get();

        this.requireResult(subcategory);

        return subcategory;
    }

}
