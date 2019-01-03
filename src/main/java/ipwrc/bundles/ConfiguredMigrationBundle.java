package ipwrc.bundles;

import ipwrc.configurations.IpwrcConfiguration;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.migrations.MigrationsBundle;

public class ConfiguredMigrationBundle extends MigrationsBundle<IpwrcConfiguration> {

    @Override
    public PooledDataSourceFactory getDataSourceFactory(IpwrcConfiguration configuration) {
        return configuration.getDataSourceFactory();
    }

}
