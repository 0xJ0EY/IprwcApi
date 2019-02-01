package ipwrc.services;

import ipwrc.models.Brand;
import ipwrc.persistence.DAO;

import javax.inject.Inject;

public class BrandService extends BaseService<Brand> {

    @Inject
    public BrandService(DAO<Brand> dao) {
        super(dao);
    }

}
