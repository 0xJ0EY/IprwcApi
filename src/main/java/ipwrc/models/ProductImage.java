package ipwrc.models;

import com.fasterxml.jackson.annotation.JsonView;
import ipwrc.views.View;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Entity(name = "ProductImage")
@Table(name = "product_images")
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @JsonView(View.Internal.class)
    private int id;

    @NotEmpty
    @Column(name = "image_name", nullable = false)
    @JsonView(View.Internal.class)
    private String imageName;

    @NotEmpty
    @Column(name = "media_type", nullable = false)
    @JsonView(View.Private.class)
    private String mediaType;

    @NotEmpty
    @Column(name = "title", nullable = false)
    @JsonView(View.Public.class)
    private String title;

    @JsonView(View.Private.class)
    public byte[] getContent() {
        // TODO: load file from file system
        return null;
//        return this.imageName;
    }

    public void setContent(byte[] content) {
        // Write byte array to file
    }


}
