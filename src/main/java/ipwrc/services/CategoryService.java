package ipwrc.services;

import ipwrc.models.Category;
import ipwrc.persistence.DAO;

import javax.inject.Inject;

public class CategoryService extends BaseService<Category> {

    @Inject
    public CategoryService(DAO<Category> dao) {
        super(dao);
    }

}
