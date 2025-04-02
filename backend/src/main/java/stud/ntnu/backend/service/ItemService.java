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

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public List<ItemPreviewDto> getAllItemPreviews() {
        List<Item> items = itemRepository.findAll();
        return items.stream()
            .map(this::mapToItemPreviewDto)
            .collect(Collectors.toList());
    }

    private ItemPreviewDto mapToItemPreviewDto(Item item) {
        String imageUrl = getFirstImageUrl(item);

        return ItemPreviewDto.builder()
            .id(item.getId())
            .title(item.getBriefDescription())
            .price(item.getPrice())
            .imageUrl(imageUrl)
            .build();
    }

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