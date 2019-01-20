package ipwrc.models;

import com.fasterxml.jackson.annotation.*;
import ipwrc.resources.ProductResource;
import ipwrc.views.View;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Product")
@Table(name = "products")
@NamedQueries({
    @NamedQuery(name = "Product.getAll", query = "SELECT p FROM Product p ORDER BY p.id DESC"),
    @NamedQuery(name = "Product.findById", query = "SELECT p FROM Product p WHERE p.id =:id"),
    @NamedQuery(name = "Product.findByTitle", query = "SELECT p FROM Product p WHERE p.title =:title")
})
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
    @JsonView(ProductResource.ProductPublicView.class)
    public Subcategory subcategory;

    @ManyToOne(
        targetEntity = Brand.class,
        cascade = { CascadeType.MERGE, CascadeType.PERSIST }
    )
    @JoinColumn(name = "fk_brand")
    @JsonView(ProductResource.ProductPublicView.class)
    private Brand brand;

//    @OneToMany()
//    @JoinColumn(name = "fk_product")
//    @JsonView(View.Public.class)
//    private List<ProductImage> images = new ArrayList<>();
//
    @Column(name = "description")
    @JsonView(View.Public.class)
    private String description;

    // The price including the vat percentage
    @NotEmpty
    @Column(name = "price")
    @JsonView(View.Public.class)
    private double price;

    // The amount of vat on the product
    @NotEmpty
    @Column(name = "vat_percentage", nullable = false)
    @JsonView(View.Public.class)
    private int vat;

    @JsonView(View.Public.class)
    public double getPriceWithoutVat() {
        float x = 1 + (float) this.vat / 100;
        return (double) Math.round((this.price / x) * 100) / 100;
    }

    private String generateTitle(String name) {
        String brand = this.brand.getTitle();

        return brand + "-" + Normalizer.normalize(name, Normalizer.Form.NFD)
            .replaceAll("[^\\p{ASCII}]", "") // Replace to ASCII
            .replace("[^A-Za-z0-9]", "-") // Replace all the unknown characters to '-'
            .replace(" ", "-")
        ;
    }

    public void setName(String name) {
        this.name = name;
        this.title = this.generateTitle(name);
    }

    @JsonView(View.Public.class)
    public String getName() {
        return this.name;
    }

    @JsonView(View.Public.class)
    public String getTitle() {
        return this.title;
    }
}
