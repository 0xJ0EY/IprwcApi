package ipwrc.models;

import com.fasterxml.jackson.annotation.JsonView;
import ipwrc.resources.CategoryResource;
import ipwrc.views.View;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Category")
@Table(name = "categories")
@NamedQueries({
    @NamedQuery(name = "Category.getAll", query = "SELECT p FROM Category p"),
    @NamedQuery(name = "Category.findByTitle", query = "SELECT c FROM Category c WHERE c.title =:title")
})
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @JsonView(View.Internal.class)
    private int id;

    @NotEmpty
    @Column(name = "name", nullable = false)
    @JsonView(View.Public.class)
    private String name;

    @NotEmpty
    @Column(name = "title", nullable = false)
    @JsonView(View.Public.class)
    private String title;

    @OneToMany
    @JoinColumn(name = "fk_category", nullable = true)
    @JsonView(CategoryResource.CategoryPublicView.class)
    private Set<Subcategory> subcategories = new HashSet<>();

}
