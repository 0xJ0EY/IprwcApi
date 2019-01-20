package ipwrc.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import ipwrc.resources.SubcategoryResource;
import ipwrc.views.View;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Subcategory")
@Table(name = "subcategories")
@NamedQueries({
    @NamedQuery(name = "Subcategory.getAll", query = "SELECT s FROM Subcategory s"),
    @NamedQuery(name = "Subcategory.findByTitle", query = "SELECT c FROM Subcategory c WHERE c.title =:title")
})
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Subcategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @JsonView(View.Public.class)
    public int id;

    @NotEmpty
    @Column(name = "name", nullable = false)
    @JsonView(View.Public.class)
    private String name;

    @NotEmpty
    @Column(name = "title", nullable = false)
    @JsonView(View.Public.class)
    private String title;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_subcategory")
    @OrderBy("id ASC")
    @JsonView(SubcategoryResource.SubcategoryPrivateView.class)
    private Set<Product> products = new HashSet<>();


}
