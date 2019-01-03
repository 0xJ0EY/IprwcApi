package ipwrc.bundles;

import ipwrc.configurations.IpwrcConfiguration;
import com.google.common.collect.ImmutableList;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.SessionFactoryFactory;

public class ConfiguredHibernateBundle extends HibernateBundle<IpwrcConfiguration> {

    public ConfiguredHibernateBundle(Class<?> entity, Class<?>... entities) {
        super(entity, entities);
    }

    public ConfiguredHibernateBundle(ImmutableList<Class<?>> entities, SessionFactoryFactory sessionFactoryFactory) {
        super(entities, sessionFactoryFactory);
    }

    @Override
    public PooledDataSourceFactory getDataSourceFactory(IpwrcConfiguration configuration) {
        return configuration.getDataSourceFactory();
    }
}
