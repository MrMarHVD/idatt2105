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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stud.ntnu.backend.data.ItemPreviewDto;
import stud.ntnu.backend.service.ItemService;

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
    return ResponseEntity.ok(itemService.getAllItemPreviews());
  }
}