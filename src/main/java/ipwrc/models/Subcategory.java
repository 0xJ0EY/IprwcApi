package ipwrc.models;

import com.fasterxml.jackson.annotation.*;
import ipwrc.helpers.TextFormatter;
import ipwrc.resources.SubcategoryResource;
import ipwrc.views.View;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Subcategory")
@Table(name = "subcategories")
@NamedQueries({
    @NamedQuery(name = "Subcategory.getAll", query = "SELECT s FROM Subcategory s ORDER BY s.id ASC"),
    @NamedQuery(name = "Subcategory.findByTitle", query = "SELECT c FROM Subcategory c WHERE c.title =:title")
})
@JsonIgnoreProperties(ignoreUnknown = true)
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

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "title", nullable = false)
    @JsonView(View.Public.class)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_category")
    @JsonView(SubcategoryResource.SubcategoryPublicView.class)
    private Category category;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_subcategory")
    @JsonView(SubcategoryResource.SubcategoryPrivateView.class)
    private Set<Product> products = new HashSet<>();

    public void setName(String name) {
        this.name = name;
        this.setTitle(TextFormatter.toTitle(name));
    }

    @JsonIgnore
    public void setTitle(String title) {
        this.title = title;
    }
}
