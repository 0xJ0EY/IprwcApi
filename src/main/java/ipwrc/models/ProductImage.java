package ipwrc.models;

import com.fasterxml.jackson.annotation.*;
import ipwrc.resources.ProductResource;
import ipwrc.views.View;
import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.validator.constraints.NotEmpty;
import org.postgresql.util.Base64;

import javax.persistence.*;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Entity(name = "ProductImage")
@Table(name = "product_images")
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @JsonView(View.Internal.class)
    private int id;

    @ManyToOne
    @JoinColumn(name = "fk_product", nullable = false)
    @JsonView(View.Internal.class)
    private Product product;

    @Column(name = "image_name", nullable = false)
    @JsonView(View.Internal.class)
    private String imageName;

    @NotEmpty
    @Column(name = "media_type", nullable = false)
    @JsonView(View.Private.class)
    private String mediaType;

    @JsonView(View.Internal.class)
    @JsonIgnore
    public byte[] getContent() {
        File image = new File("./images/" + this.getImageName());

        try (FileInputStream fis = new FileInputStream(image)) {

            byte[] content = new byte[(int)image.length()];

            int i = fis.read(content);

            return content;

        } catch (IOException e) {
            throw new WebApplicationException(e);
        }

    }

    /**
     * Send base64 images back so we can reuse the original images from the edit view
     */
    @JsonProperty("content")
    @JsonView(ProductResource.ProductEditView.class)
    private String getContentImageString() {
        String output = "data:";
        output += this.getMediaType();
        output += ";base64,";
        output += Base64.encodeBytes(this.getContent(), Base64.DONT_BREAK_LINES);

        return output;
    }

    @JsonView(View.Internal.class)
    public int getId() {
        return id;
    }

    @JsonView(View.Public.class)
    @JsonProperty("imageName")
    private String getExternalImageName() {
        StringBuilder sb = new StringBuilder();

        int index = this.product.findProductImageIndex(this);

        if (index == -1) throw new NotFoundException();

        sb.append("products/");
        sb.append(this.product.getTitle());
        sb.append("/image/");
        sb.append(index);

        return sb.toString();
    }

    private String getImageName() {
        if (this.imageName == null || this.imageName.length() == 0)
            this.generateImageName();

        return imageName;
    }

    /**
     * Generate an image name based on a given hash and current time
     * @param
     */
    private void generateImageName() {
        DateFormat df = new SimpleDateFormat("yyyyddMMHHmmss");
        Date now = Calendar.getInstance().getTime();

        this.imageName = (df.format(now) + RandomStringUtils.randomAlphanumeric(16));
    }

    @SuppressWarnings("unstable")
    @JsonView(View.Private.class)
    public void setContent(String content) {
        String[] parts = content.split(",");

        String mediaType    = parts[0].split(":")[1].split(";")[0];
        String base64       = parts[1];

        this.setMediaType(mediaType);

        byte[] data = Base64.decode(base64);

        // Write file
        try (OutputStream os = new FileOutputStream("./images/" + this.getImageName())) {
            os.write(data);

        } catch (IOException e) {
            throw new WebApplicationException(e);
        }

    }

    public String getMediaType() {
        return mediaType;
    }

    @JsonIgnore
    public void setMediaType(String mediaType) {
        if (!mediaType.startsWith("image/"))
            throw new WebApplicationException(Response.Status.NOT_ACCEPTABLE);

        this.mediaType = mediaType;
    }

    @JsonView(View.Internal.class)
    public Product getProduct() {
        return product;
    }

    @JsonView(View.Internal.class)
    public void setProduct(Product product) {
        this.product = product;
    }
}
