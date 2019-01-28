package ipwrc.auth;

import ipwrc.models.User;
import ipwrc.persistence.UserDAO;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.hibernate.UnitOfWork;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

public class BasicAuthenticator implements Authenticator<BasicCredentials, User> {


    private UserDAO dao;

    public BasicAuthenticator(UserDAO dao) {
        this.dao = dao;
    }

    @UnitOfWork
    @Override
    public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {

        User u = this.dao.getByUsername(credentials.getUsername());
        Optional<User> user = u != null ? Optional.of(u) : Optional.empty();

        // If there is no user found, give back an empty user
        if ( ! user.isPresent()) return Optional.empty();

        if (BCrypt.checkpw(credentials.getPassword(), user.get().getPassword())) {
            return user;
        } else {
            return Optional.empty();
        }
    }

}
