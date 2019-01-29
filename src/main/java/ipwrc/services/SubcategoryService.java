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

}
