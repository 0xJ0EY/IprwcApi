package ipwrc.models;

import ipwrc.views.View;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "role")
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @JsonView(View.Internal.class)
    private int id;

    @NotEmpty
    @Column(name = "name", nullable = false)
    @JsonView(View.Public.class)
    private String name;


//    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
//    @JsonView(View.Internal.class)
//    private Set<User> users = new HashSet<User>();

    public String getName() {
        return name;
    }
}
