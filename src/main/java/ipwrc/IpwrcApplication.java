package ipwrc;

import io.dropwizard.hibernate.AbstractDAO;
import ipwrc.auth.BasicAuthenticator;
import ipwrc.auth.BasicAuthorizer;
import ipwrc.bundles.ConfiguredHibernateBundle;
import ipwrc.bundles.ConfiguredMigrationBundle;
import ipwrc.configurations.IpwrcConfiguration;
import ipwrc.models.*;
import ipwrc.persistence.CategoryDAO;
import ipwrc.persistence.UserDAO;
import ipwrc.filters.CORSFilter;
import ipwrc.resources.AuthResource;
import ipwrc.resources.CategoryResource;
import ipwrc.resources.UserResource;
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
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import java.util.HashMap;

public class IpwrcApplication extends Application<IpwrcConfiguration> {

    private final ConfiguredHibernateBundle userHibernateBundle = new ConfiguredHibernateBundle(
        User.class,
        Role.class,
        Category.class,
        Subcategory.class
    );

    private enum DaoName { USER, CATEGORY }
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
        bootstrap.addBundle(new IndexPageBundle("/assets/index.html", ImmutableSet.of("/login", "/app/*")));
        bootstrap.addBundle(new ConfiguredAssetsBundle("/assets", "/", "index.html"));
    }

    @Override
    public void run(IpwrcConfiguration configuration,
                    Environment environment) throws Exception {

        // Do general setup
        this.setupFilters(environment);
        this.setupDaos(environment);
        this.setupResources(environment);
        this.setupAuthentication(environment);
    }

    private void setupFilters(Environment environment) {
        environment.jersey().register(new CORSFilter());
    }

    private void setupDaos(Environment environment) {
        this.daos.put(DaoName.USER, new UserDAO(this.userHibernateBundle.getSessionFactory()));
        this.daos.put(DaoName.CATEGORY, new CategoryDAO(this.userHibernateBundle.getSessionFactory()));
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
        environment.jersey().register(new AuthResource((UserDAO) this.daos.get(DaoName.USER)));
        environment.jersey().register(new UserResource((UserDAO) this.daos.get(DaoName.USER)));
        environment.jersey().register(new CategoryResource((CategoryDAO) this.daos.get(DaoName.CATEGORY)));
    }
}
