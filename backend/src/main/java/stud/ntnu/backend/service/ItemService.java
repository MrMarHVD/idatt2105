package stud.ntnu.backend.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import stud.ntnu.backend.data.ItemDetailsDto;
import stud.ntnu.backend.data.ItemPreviewDto;
import stud.ntnu.backend.model.Item;
import stud.ntnu.backend.model.ItemImage;
import stud.ntnu.backend.model.ItemView;
import stud.ntnu.backend.model.User;
import stud.ntnu.backend.repository.ItemRepository;
import stud.ntnu.backend.repository.ItemViewRepository;
import stud.ntnu.backend.repository.UserRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <h2>ItemService</h2>
 * <p>Service responsible for business logic related to marketplace items.</p>
 * <p>This service provides methods for retrieving and processing item data,
 * abstracting database access through the {@link ItemRepository} and transforming database entities
 * into DTOs suitable for client consumption.</p>
 */
@Service
@RequiredArgsConstructor
public class ItemService {

  /**
   * <h3>Item repository for accessing item data</h3>
   * <p>JPA repository that provides database operations for items.</p>
   */
  private final ItemRepository itemRepository;

  private final ItemViewRepository itemViewRepository;

  private final UserRepository userRepository;
  
  public Item findById(Long id) {
    return itemRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Item not found with id: " + id));
  }

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
   * price, and first image URL
   */
  private ItemPreviewDto mapToItemPreviewDto(Item item) {
    String imageUrl = getFirstImageUrl(item);

    return ItemPreviewDto.builder()
        .id(item.getId())
        .title(item.getBriefDescription())
        .price(item.getPrice())
        .imageUrl(imageUrl)
        .latitude(item.getLatitude())
        .longitude(item.getLongitude())
        .build();
  }

  /**
   * <h3>Retrieve detailed information for a specific item</h3>
   * <p>Fetches a single item by its ID and transforms it into a comprehensive
   * details DTO containing all relevant information for display.</p>
   *
   * @param id the unique identifier of the item to retrieve
   * @return an {@link ItemDetailsDto} containing all details of the requested item
   * @throws RuntimeException if the item with the given ID is not found
   */
  public ItemDetailsDto getItemDetailsById(Long id) {
    Item item = itemRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Item not found with id: " + id));

    return mapToItemDetailsDto(item);
  }

  /**
   * <h3>Map an Item entity to its detailed DTO</h3>
   * <p>Transforms a complete {@link Item} entity into a comprehensive
   * {@link ItemDetailsDto} containing all relevant display data.</p>
   *
   * @param item the item entity to transform
   * @return an {@link ItemDetailsDto} containing the item's complete information
   */
  private ItemDetailsDto mapToItemDetailsDto(Item item) {
    List<String> imageUrls = item.getImages() != null ?
        item.getImages().stream()
            .sorted(Comparator.comparing(ItemImage::getPosition))
            .map(ItemImage::getImageUrl)
            .collect(Collectors.toList()) :
        List.of();

    return ItemDetailsDto.builder()
        .id(item.getId())
        .title(item.getBriefDescription())
        .description(item.getFullDescription())
        .category(item.getCategory() != null ? item.getCategory().getName() : "")
        .price(item.getPrice())
        .contact(item.getSeller() != null ? item.getSeller().getDisplayName() : "")
        .imageUrls(imageUrls)
        .build();
  }

  /**
   * <h3>Retrieve items by category ID</h3>
   * <p>Fetches items that belong to a specific category and transforms them into
   * lightweight preview DTOs containing only essential information.</p>
   *
   * @param categoryId the ID of the category to filter items by
   * @return a list of {@link ItemPreviewDto} objects containing preview information for items in
   * the requested category
   */
  public List<ItemPreviewDto> getItemsByCategory(Long categoryId) {
    List<Item> items = itemRepository.findByCategoryId(categoryId);
    return items.stream()
        .map(this::mapToItemPreviewDto)
        .collect(Collectors.toList());
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

  /**
   * Record a view of an item by a user
   *
   * @param itemId    the ID of the viewed item
   * @param userEmail the email of the user viewing the item
   */
  public void recordView(Long itemId, String userEmail) {
    Item item = itemRepository.findById(itemId)
        .orElseThrow(() -> new RuntimeException("Item not found with id: " + itemId));

    User user = userRepository.findByEmail(userEmail)
        .orElseThrow(() -> new RuntimeException("User not found with email: " + userEmail));

    ItemView itemView = new ItemView();
    itemView.setItem(item);
    itemView.setUser(user);

    itemViewRepository.save(itemView);
  }

  /**
   * <h3>Get items based on a category probability distribution</h3>
   * <p>Returns a list of items randomly selected according to the provided category
   * distribution.</p>
   * TODO: consider splitting this function into multiple smaller functions for better readability
   *
   * @param distribution Map of category IDs to their probabilities
   * @param limit        Maximum number of items to return
   * @return List of item previews selected based on the distribution
   */
  public List<ItemPreviewDto> getItemsByDistribution(Map<String, Double> distribution,
      Integer limit) {
    // Default limit if not provided or invalid
    int itemLimit = (limit != null && limit > 0) ? Math.min(limit, 1000) : 1000;

    Map<Long, List<Item>> categoryItemsMap = new HashMap<>();
    List<ItemPreviewDto> result = new ArrayList<>();
    Random random = new Random();

    // Group all items by category
    for (String categoryIdStr : distribution.keySet()) {
      try {
        Long categoryId = Long.parseLong(categoryIdStr);
        List<Item> items = itemRepository.findByCategoryId(categoryId);
        if (!items.isEmpty()) {
          categoryItemsMap.put(categoryId, items);
        }
      } catch (NumberFormatException e) {
        // Skip invalid category IDs
        continue;
      }
    }

    // If no valid categories or all categories empty, return empty list
    if (categoryItemsMap.isEmpty()) {
      return result;
    }

    // Calculate number of items to select from each category based on distribution
    Map<Long, Integer> categoryItemCounts = new HashMap<>();
    for (Map.Entry<String, Double> entry : distribution.entrySet()) {
      try {
        Long categoryId = Long.parseLong(entry.getKey());
        if (categoryItemsMap.containsKey(categoryId)) {
          int itemCount = (int) Math.ceil(itemLimit * entry.getValue());
          categoryItemCounts.put(categoryId, itemCount);
        }
      } catch (NumberFormatException e) {
        continue;
      }
    }

    // Random selection process
    for (Map.Entry<Long, Integer> entry : categoryItemCounts.entrySet()) {
      Long categoryId = entry.getKey();
      int itemCount = entry.getValue();
      List<Item> categoryItems = categoryItemsMap.get(categoryId);

      // Select random items from this category
      for (int i = 0; i < itemCount && result.size() < itemLimit; i++) {
        if (categoryItems.isEmpty()) {
          break;
        }

        int randomIndex = random.nextInt(categoryItems.size());
        Item selectedItem = categoryItems.get(randomIndex);

        // Add to results and remove to avoid duplicates
        result.add(mapToItemPreviewDto(selectedItem));
        categoryItems.remove(randomIndex);
      }
    }

    // Shuffle the final results for better randomness
    Collections.shuffle(result);

    // Ensure we don't exceed the limit
    if (result.size() > itemLimit) {
      return result.subList(0, itemLimit);
    }

    return result;
  }
}