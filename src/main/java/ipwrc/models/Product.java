package ipwrc.models;

import com.fasterxml.jackson.annotation.*;
import ipwrc.helpers.TextFormatter;
import ipwrc.resources.ProductResource;
import ipwrc.views.View;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Product")
@Table(name = "products")
@NamedQueries({
    @NamedQuery(name = "Product.getAll", query = "SELECT p FROM Product p ORDER BY p.id DESC"),
    @NamedQuery(name = "Product.findById", query = "SELECT p FROM Product p WHERE p.id =:id"),
    @NamedQuery(name = "Product.findByTitle", query = "SELECT p FROM Product p WHERE p.title =:title")
})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Product {

    @Id
    @JsonView(View.Public.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @NotEmpty
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "title", nullable = false)
    private String title;

    @ManyToOne(
        targetEntity = Subcategory.class,
        cascade = { CascadeType.MERGE, CascadeType.PERSIST }
    )
    @JoinColumn(name = "fk_subcategory")
    @JsonView(ProductResource.ProductPrivateView.class)
    public Subcategory subcategory;

    @ManyToOne(
        targetEntity = Brand.class,
        cascade = { CascadeType.MERGE, CascadeType.PERSIST }
    )
    @JoinColumn(name = "fk_brand")
    @JsonView(View.Public.class)
    private Brand brand;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    @JsonView(View.Public.class)
    private List<ProductImage> images = new ArrayList<>();

    @Column(name = "description")
    @JsonView(View.Public.class)
    private String description;

    // The price including the vat percentage
    @NotNull
    @Column(name = "price")
    @JsonView(View.Public.class)
    private double price;

    // The amount of vat on the product
    @Min(0)
    @Max(100)
    @NotNull
    @Column(name = "vat_percentage", nullable = false)
    @JsonView(View.Public.class)
    private int vat;

    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    @JsonView(View.Public.class)
    public double getPriceWithoutVat() {
        float x = 1 + (float) this.vat / 100;
        return (double) Math.round((this.price / x) * 100) / 100;
    }

    @JsonView(View.Public.class)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
        this.setTitle(TextFormatter.toTitle(this.brand.getTitle() + " " + name));
    }

    @JsonView(View.Public.class)
    public List<ProductImage> getImages() {
        return images;
    }

    public void setImages(List<ProductImage> images) {
        this.images = images;
    }

    @JsonView(View.Public.class)
    public String getTitle() {
        return this.title;
    }

    @JsonIgnore
    public void setTitle(String title) {
        this.title = title;
    }

    public int findProductImageIndex(ProductImage image) {

        for (int i = 0; i < this.images.size(); i++) {
            if (image.getId() == this.images.get(i).getId())
                return i + 1;
        }

        return -1;
    }


}
