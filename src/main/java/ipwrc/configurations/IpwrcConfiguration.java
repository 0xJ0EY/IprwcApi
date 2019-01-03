package ipwrc.configurations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.palantir.indexpage.IndexPageConfigurable;
import io.dropwizard.Configuration;
import io.dropwizard.bundles.assets.AssetsBundleConfiguration;
import io.dropwizard.bundles.assets.AssetsConfiguration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

public class IpwrcConfiguration extends Configuration implements AssetsBundleConfiguration, IndexPageConfigurable {

    private final String indexPagePath;

    @Valid
    @NotNull
    @JsonProperty
    private ApplicationConfiguration application = new ApplicationConfiguration();

    @Valid
    @NotNull
    @JsonProperty
    private final AssetsConfiguration assets = AssetsConfiguration.builder().build();

    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();

    public ApplicationConfiguration getApplicationConfiguration() {
        return this.application;
    }

    @Override
    public AssetsConfiguration getAssetsConfiguration() {
        return this.assets;
    }

    @JsonCreator
    IpwrcConfiguration(@JsonProperty("indexPagePath") Optional<String> indexPagePath) {
        this.indexPagePath = indexPagePath.orElse("../frontend/index.html");
    }

    @Override
    public String getIndexPagePath() {
        return this.indexPagePath;
    }

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
        this.database = dataSourceFactory;
    }
}
