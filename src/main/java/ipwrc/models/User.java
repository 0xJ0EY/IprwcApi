package ipwrc.models;

import ipwrc.views.View;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "User")
@Table(name = "users")
@NamedQueries({
    @NamedQuery(
        name = "User.findByCredentials",
        query = "SELECT p FROM User p WHERE p.username =:username"
    ),
    @NamedQuery(name = "User.findAll", query = "SELECT p FROM User p")
})
public class User implements Principal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @JsonView(View.Internal.class)
    private int id;

    @NotEmpty
    @Column(name = "username", nullable = false)
    @JsonView(View.Public.class)
    private String username;

    @NotEmpty
    @Column(name = "password", nullable = false)
    @JsonView(View.Internal.class)
    private String password;

    @NotEmpty
    @Column(name = "email", nullable = false)
    @JsonView(View.Public.class)
    private String email;

    @ManyToMany(cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE
    }, fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = { @JoinColumn(name = "fk_user") },
        inverseJoinColumns = { @JoinColumn(name = "fk_role") }
    )
    @JsonView(View.Public.class)
    private Set<Role> roles = new HashSet<>();

    @JsonView(View.Internal.class)
    @Override
    public String getName() {
        return this.username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public boolean hasRole(String role) {

        for (Role set : this.roles) {
            if (set.getName().toLowerCase().equals(role.toLowerCase()))
                return true;
        }

        return false;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof User))
            return false;

        return ((User) object).id == this.id;
    }
}
