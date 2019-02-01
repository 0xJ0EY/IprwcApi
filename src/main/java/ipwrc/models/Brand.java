package ipwrc.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import ipwrc.helpers.TextFormatter;
import ipwrc.views.View;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Entity(name = "Brand")
@Table(name = "brands")
@NamedQueries({
    @NamedQuery(name = "Brand.getAll", query = "SELECT b FROM Brand b ORDER BY b.id ASC"),
    @NamedQuery(name = "Brand.findByTitle", query = "SELECT b FROM Brand b WHERE b.title =:title")
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Brand {

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

    public void setName(String name) {
        this.name = name;
        this.setTitle(TextFormatter.toTitle(name));
    }

    public String getTitle() {
        return title;
    }

    @JsonIgnore
    public void setTitle(String title) {
        this.title = title;
    }
}
