package ipwrc.models;

import com.fasterxml.jackson.annotation.JsonView;
import ipwrc.views.View;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Entity(name = "Product")
@Table(name = "products")
public class Product {

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

    @NotEmpty
    @Column(name = "description")
    @JsonView(View.Public.class)
    private String description;

    // The price without vat calculated on it

    @NotEmpty
    @Column(name = "price")
    @JsonView(View.Internal.class)
    private double price;

    // The amount of vat on the product
    @NotEmpty
    @Column(name = "vat_percentage", nullable = false)
    @JsonView(View.Public.class)
    private int vat;

    @JsonView(View.Public.class)
    public double getPrice() {
        return this.price;
    }

    @JsonView(View.Public.class)
    public double getPriceWithoutVat() {
        float x = 1 + (float) this.vat / 100;
        return (double) Math.round((this.price / x) * 100) / 100;
    }

}
