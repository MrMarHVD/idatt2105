package stud.ntnu.backend.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
// TODO: add docs
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemPreviewDto {
    private Long id;
    private String title;  // Will map from briefDescription
    private String imageUrl;
    private BigDecimal price;
}