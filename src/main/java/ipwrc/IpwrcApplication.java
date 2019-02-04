package ipwrc;

import io.dropwizard.hibernate.AbstractDAO;
import io.dropwizard.jersey.jackson.JsonProcessingExceptionMapper;
import ipwrc.auth.BasicAuthenticator;
import ipwrc.auth.BasicAuthorizer;
import ipwrc.bundles.ConfiguredHibernateBundle;
import ipwrc.bundles.ConfiguredMigrationBundle;
import ipwrc.configurations.IpwrcConfiguration;
import ipwrc.models.*;
import ipwrc.persistence.*;
import ipwrc.filters.CORSFilter;
import ipwrc.resources.*;
import com.google.common.collect.ImmutableSet;
import com.palantir.indexpage.IndexPageBundle;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.bundles.assets.ConfiguredAssetsBundle;
import io.dropwizard.forms.MultiPartBundle;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import ipwrc.services.*;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.mindrot.jbcrypt.BCrypt;

import java.util.HashMap;

public class IpwrcApplication extends Application<IpwrcConfiguration> {

    private final ConfiguredHibernateBundle userHibernateBundle = new ConfiguredHibernateBundle(
        User.class,
        Role.class,
        Category.class,
        Subcategory.class,
        Product.class,
        ProductImage.class,
        Brand.class,
        Order.class,
        OrderItem.class
    );

    private enum DaoName { USER, CATEGORY, SUBCATEGORY, PRODUCT, BRAND, ORDER }
    private HashMap<DaoName, AbstractDAO> daos = new HashMap<>();

    public static void main(String[] args) throws Exception {
        new IpwrcApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<IpwrcConfiguration> bootstrap) {
        super.initialize(bootstrap);

        // Setup migration bundle
        bootstrap.addBundle(new ConfiguredMigrationBundle());

        bootstrap.addBundle(new MultiPartBundle());

        // Setup hibernate bundles for the DAOS
        bootstrap.addBundle(this.userHibernateBundle);

        // Bootstrapping the Angular SPA
        bootstrap.addBundle(new IndexPageBundle("/assets/index.html", ImmutableSet.of(
                "/login",
                "/categorie/*",
                "/product/*",
                "/winkelwagen",
                "/bestellen",
                "/bestellingen/*",
                "/admin/*"
        )));
        bootstrap.addBundle(new ConfiguredAssetsBundle("/assets", "/", "index.html"));
    }

    @Override
    public void run(IpwrcConfiguration configuration,
                    Environment environment) throws Exception {

        // Do general setup
        this.setupDaos(environment);
        this.setupResources(environment);
        this.setupAuthentication(environment);

        if (configuration.getApplicationConfiguration().isDevelopment()) {
            this.setupDevelopmentEnvironment(environment);
        }
    }

    private void setupDaos(Environment environment) {
        this.daos.put(DaoName.USER, new UserDAO(this.userHibernateBundle.getSessionFactory()));
        this.daos.put(DaoName.CATEGORY, new CategoryDAO(this.userHibernateBundle.getSessionFactory()));
        this.daos.put(DaoName.SUBCATEGORY, new SubcategoryDAO(this.userHibernateBundle.getSessionFactory()));
        this.daos.put(DaoName.PRODUCT, new ProductDAO(this.userHibernateBundle.getSessionFactory()));
        this.daos.put(DaoName.BRAND, new BrandDAO(this.userHibernateBundle.getSessionFactory()));
        this.daos.put(DaoName.ORDER, new OrderDAO(this.userHibernateBundle.getSessionFactory()));
    }

    private void setupDevelopmentEnvironment(Environment environment) {

        // Enable cors from everywhere
        environment.jersey().register(new CORSFilter());

        // Enable more JSON processing details
        environment.jersey().register(new JsonProcessingExceptionMapper(true));
    }

    private void setupAuthentication(Environment environment) {
        // Create proxy class
        BasicAuthenticator proxyAuth = new UnitOfWorkAwareProxyFactory(this.userHibernateBundle)
            .create(BasicAuthenticator.class, UserDAO.class, (UserDAO) this.daos.get(DaoName.USER));

        environment.jersey().register(new AuthDynamicFeature(
            new BasicCredentialAuthFilter.Builder<User>()
                .setAuthenticator(proxyAuth)
                .setAuthorizer(new BasicAuthorizer())
                .setPrefix("Basic")
                .buildAuthFilter()));

        environment.jersey().register(RolesAllowedDynamicFeature.class);
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
    }

    private void setupResources(Environment environment) {
        // Create services for the resources
        UserService userService = new UserService((UserDAO) this.daos.get(DaoName.USER));
        CategoryService categoryService = new CategoryService((CategoryDAO) this.daos.get(DaoName.CATEGORY));
        SubcategoryService subcategoryService = new SubcategoryService((SubcategoryDAO) this.daos.get(DaoName.SUBCATEGORY));
        ProductService productService = new ProductService((ProductDAO) this.daos.get(DaoName.PRODUCT));
        BrandService brandService = new BrandService((BrandDAO) this.daos.get(DaoName.BRAND));
        OrderService orderService = new OrderService(
                (OrderDAO) this.daos.get(DaoName.ORDER),
                (ProductDAO) this.daos.get(DaoName.PRODUCT)
        );

        // Register the resources
        environment.jersey().register(new AuthResource(userService));
        environment.jersey().register(new UserResource(userService));
        environment.jersey().register(new CategoryResource(categoryService));
        environment.jersey().register(new SubcategoryResource(subcategoryService));
        environment.jersey().register(new ProductResource(productService));
        environment.jersey().register(new BrandResource(brandService));
        environment.jersey().register(new OrderResource(orderService));
    }
}
