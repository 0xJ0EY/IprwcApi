package ipwrc.models;

import com.fasterxml.jackson.annotation.*;
import ipwrc.views.View;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "OrderItem")
@Table(name = "order_items")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @JsonView(View.Public.class)
    private int id;

    @ManyToOne
    @JoinColumn(name = "fk_order")
    private Order order;

    @JsonView(View.Public.class)
    @ManyToOne(targetEntity = Product.class, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fk_product")
    private Product product;

    @NotEmpty
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @JsonView(View.Public.class)
    @Column(name = "price", nullable = false)
    private double price;

    @NotNull
    @JsonView(View.Public.class)
    @Column(name = "amount", nullable = false)
    private int amount;

    public Order getOrder() {
        return order;
    }

    @JsonIgnore
    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;

        System.out.println("product.getName() = " + product.getName());

        this.setName(product.getName());
        this.setPrice(product.getPrice());
    }

    @JsonProperty
    @JsonView(View.Public.class)
    public String getName() {
        return name;
    }

    @JsonIgnore
    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    @JsonIgnore
    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
