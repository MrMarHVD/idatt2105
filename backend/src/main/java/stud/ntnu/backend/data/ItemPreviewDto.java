package stud.ntnu.backend.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

/**
 * <h2>ItemPreviewDto</h2>
 * <p>Data Transfer Object that contains lightweight preview information about marketplace items.</p>
 * <p>This DTO is designed to carry only essential item data needed for displaying item previews
 * in listing pages, reducing payload size and improving frontend performance.</p>
 * <p>The object is mapped from the full {@link stud.ntnu.backend.model.Item} entity
 * in the {@link stud.ntnu.backend.service.ItemService} class.</p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemPreviewDto {

    /**
     * <h3>Unique identifier for the item</h3>
     * <p>Corresponds to the primary key in the items table.</p>
     */
    private Long id;

    /**
     * <h3>Short title describing the item</h3>
     * <p>Mapped from the briefDescription field of the Item entity.</p>
     */
    private String title;

    /**
     * <h3>URL to the primary image of the item</h3>
     * <p>References the first (lowest position) image associated with the item.</p>
     * <p>May be null if the item has no images.</p>
     */
    private String imageUrl;

    /**
     * <h3>Price of the item</h3>
     * <p>Uses BigDecimal for precise monetary calculations.</p>
     */
    private BigDecimal price;
}