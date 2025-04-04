package stud.ntnu.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stud.ntnu.backend.data.ItemPreviewDto;
import stud.ntnu.backend.data.ItemDetailsDto;
import stud.ntnu.backend.data.RecommendedItemsRequestDto;
import stud.ntnu.backend.service.ItemService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import stud.ntnu.backend.model.User;
import stud.ntnu.backend.data.ItemViewRequestDto;
import stud.ntnu.backend.service.UserService;

import java.util.List;

/**
 * <h2>ItemController</h2>
 * <p>REST controller that handles item-related operations.</p>
 */
@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
@Tag(name = "Item Management", description = "Operations related to item management")
public class ItemController {

  /**
   * <h3>The ItemService.</h3>
   */
  private final ItemService itemService;

  private final UserService userService;

  /**
   * <h3>Get all items preview information.</h3>
   *
   * @return A list of items with preview information.
   */
  @Operation(summary = "Get all items", description = "Returns preview information (image, title, price) for all items")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved item list",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = List.class))),
      @ApiResponse(responseCode = "401", description = "Unauthorized"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping("/all")
  public ResponseEntity<List<ItemPreviewDto>> getAllItems() {
    return ResponseEntity.ok(itemService.getAllItemPreviews()); // TODO: add error handling
  }

  /**
   * <h3>Get item details by ID.</h3>
   *
   * @param id The ID of the item.
   * @return The item details.
   */
  @GetMapping("/details/{id}")
  public ResponseEntity<ItemDetailsDto> getItemDetails(@PathVariable Long id) {
    return ResponseEntity.ok(itemService.getItemDetailsById(id)); // TODO: add error handling
  }

  /**
   * <h3>Get items by category.</h3>
   *
   * @param category The category ID.
   * @return A list of items in the specified category.
   */
  @GetMapping("/category/{category}")
  public ResponseEntity<List<ItemPreviewDto>> getItemsByCategory(@PathVariable Long category) {
    return ResponseEntity.ok(itemService.getItemsByCategory(category)); // TODO: add error handling
  }


  @PostMapping("/view/post/{id}")
  public ResponseEntity<Void> recordItemView(@PathVariable Long id) {
    // Get currently authenticated user email
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();

    // Let the service handle user lookup
    itemService.recordView(id, email);

    return ResponseEntity.noContent().build();
  }

  @PostMapping("/view/recommended_items")
  public ResponseEntity<List<ItemPreviewDto>> getRecommendedItems(
      @RequestBody RecommendedItemsRequestDto requestDto) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName();

    return ResponseEntity.ok(
        itemService.getItemsByDistribution(requestDto.getDistribution(), requestDto.getLimit())
    );

  }
}