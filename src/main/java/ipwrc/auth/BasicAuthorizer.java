package ipwrc.auth;

import ipwrc.models.User;
import io.dropwizard.auth.Authorizer;

public class BasicAuthorizer implements Authorizer<User> {

    @Override
    public boolean authorize(User principal, String role) {
        return principal.hasRole(role);
    }

}
