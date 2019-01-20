package ipwrc.configurations;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

public class ApplicationConfiguration extends Configuration {

    @NotEmpty
    @JsonProperty
    private String host;

    @NotEmpty
    @JsonProperty
    private String environment;

    public String getHost() {
        return host;
    }

    public boolean isDevelopment() {
        return environment.toLowerCase().equals("development");
    }
}
