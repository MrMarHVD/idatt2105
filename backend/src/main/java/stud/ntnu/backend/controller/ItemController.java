package stud.ntnu.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stud.ntnu.backend.data.ItemPreviewDto;
import stud.ntnu.backend.service.ItemService;

import java.util.List;


/**
 * <h2> The ItemController class is a REST controller that handles HTTP requests regarding items.</h2>
 */
@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {

  /**
   * <h3> The ItemService. </h3>
   */
  private final ItemService itemService;

  /**
   * Get preview information (image, title, price) for all items.
   * @return A list of {@link ItemPreviewDto} objects containing preview information for all items.
   */
  @GetMapping("/all")
  public ResponseEntity<List<ItemPreviewDto>> getAllItems() {
    return ResponseEntity.ok(itemService.getAllItemPreviews());
  }
}