package ipwrc.configurations;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

public class ApplicationConfiguration extends Configuration {

    @NotEmpty
    @JsonProperty
    private String host;

    public String getHost() {
        return host;
    }
}
