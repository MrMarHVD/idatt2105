package stud.ntnu.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import stud.ntnu.backend.data.ItemPreviewDto;
import stud.ntnu.backend.model.Item;
import stud.ntnu.backend.model.ItemImage;
import stud.ntnu.backend.repository.ItemRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <h2>ItemService</h2>
 * <p>Service responsible for business logic related to marketplace items.</p>
 * <p>This service provides methods for retrieving and processing item data,
 * abstracting database access through the {@link ItemRepository} and transforming
 * database entities into DTOs suitable for client consumption.</p>
 */
@Service
@RequiredArgsConstructor
public class ItemService {

    /**
     * <h3>Item repository for accessing item data</h3>
     * <p>JPA repository that provides database operations for items.</p>
     */
    private final ItemRepository itemRepository;

    /**
     * <h3>Retrieve all items in preview format</h3>
     * <p>Fetches all items from the database and transforms them into
     * lightweight preview DTOs containing only essential information.</p>
     *
     * @return a list of {@link ItemPreviewDto} objects containing preview information for all items
     */
    public List<ItemPreviewDto> getAllItemPreviews() {
        List<Item> items = itemRepository.findAll();
        return items.stream()
            .map(this::mapToItemPreviewDto)
            .collect(Collectors.toList());
    }

    /**
     * <h3>Map an Item entity to its preview DTO</h3>
     * <p>Transforms a complete {@link Item} entity into a simplified
     * {@link ItemPreviewDto} containing only the data needed for item listings.</p>
     *
     * @param item the item entity to transform
     * @return an {@link ItemPreviewDto} containing the item's ID, brief description (as title),
     *         price, and first image URL
     */
    private ItemPreviewDto mapToItemPreviewDto(Item item) {
        String imageUrl = getFirstImageUrl(item);

        return ItemPreviewDto.builder()
            .id(item.getId())
            .title(item.getBriefDescription())
            .price(item.getPrice())
            .imageUrl(imageUrl)
            .build();
    }

    /**
     * <h3>Extract the URL of the first image for an item</h3>
     * <p>Finds the image with the lowest position value from the item's
     * associated images and returns its URL.</p>
     *
     * @param item the item entity to extract an image URL from
     * @return the URL string of the first image (sorted by position) or null if no images exist
     */
    private String getFirstImageUrl(Item item) {
        if (item.getImages() == null || item.getImages().isEmpty()) {
            return null;
        }

        return item.getImages().stream()
            .min(Comparator.comparing(ItemImage::getPosition))
            .map(ItemImage::getImageUrl)
            .orElse(null);
    }
}