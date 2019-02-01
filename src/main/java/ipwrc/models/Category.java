package ipwrc.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import ipwrc.helpers.TextFormatter;
import ipwrc.resources.CategoryResource;
import ipwrc.views.View;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Category")
@Table(name = "categories")
@NamedQueries({
    @NamedQuery(name = "Category.getAll", query = "SELECT p FROM Category p ORDER BY p.id ASC"),
    @NamedQuery(name = "Category.findByTitle", query = "SELECT c FROM Category c WHERE c.title =:title")
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @JsonView(View.Public.class)
    private int id;

    @NotEmpty
    @Column(name = "name", nullable = false)
    @JsonView(View.Public.class)
    private String name;

    @Column(name = "title", nullable = false)
    @JsonView(View.Public.class)
    private String title;

    @OneToMany
    @JoinColumn(name = "fk_category", nullable = true)
    @JsonView(CategoryResource.CategoryPublicView.class)
    private Set<Subcategory> subcategories = new HashSet<>();

    public void setName(String name) {
        this.name = name;
        this.title = TextFormatter.toTitle(name);
    }
}
