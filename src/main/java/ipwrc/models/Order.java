package ipwrc.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import ipwrc.views.View;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Order")
@Table(name = "orders")
@NamedQueries({
    @NamedQuery(name = "Order.getAll", query = "SELECT o FROM Order o ORDER BY o.id DESC"),
    @NamedQuery(name = "Order.findById", query = "SELECT o FROM Order o WHERE o.id =:id"),
    @NamedQuery(name = "Order.findPersonal", query = "SELECT o FROM Order o WHERE o.user.id =:id"),
    @NamedQuery(name = "Order.findPersonalById", query = "SELECT o FROM Order o WHERE o.id =:id AND o.user.id =:user_id")
})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Order {

    @Id
    @JsonView(View.Public.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @NotNull
    @ManyToOne(targetEntity = User.class, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fk_user")
    @JsonView(View.Internal.class)
    private User user;

    @NotEmpty
    @Column(name = "first_name", nullable = false)
    @JsonView(View.Public.class)
    private String firstname;

    @NotEmpty
    @Column(name = "last_name", nullable = false)
    @JsonView(View.Public.class)
    private String lastname;

    @NotEmpty
    @Column(name = "street", nullable = false)
    @JsonView(View.Public.class)
    private String street;

    @NotEmpty
    @Column(name = "house_number", nullable = false)
    @JsonView(View.Public.class)
    private String houseNumber;

    @NotEmpty
    @Column(name = "postal_code", nullable = false)
    @JsonView(View.Public.class)
    private String postalCode;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    @JsonView(View.Public.class)
    private List<OrderItem> items = new ArrayList<>();


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}

