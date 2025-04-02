package stud.ntnu.backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "seller_id")
  private User seller;

  @ManyToOne(optional = false)
  @JoinColumn(name = "category_id")
  private Category category;

  @Column(nullable = false)
  private String briefDescription;

  @Column(columnDefinition = "TEXT")
  private String fullDescription;

  @Column(nullable = false)
  private BigDecimal price;

  private Double latitude;
  private Double longitude;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ItemStatus status;

  @Column(nullable = false, updatable = false)
  private LocalDateTime publishedAt;

  @Column(nullable = false)
  private LocalDateTime updatedAt;

  @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ItemImage> images;

  @PrePersist
  void onCreate() {
    publishedAt = updatedAt = LocalDateTime.now();
    status = ItemStatus.ACTIVE;
  }

  @PreUpdate
  void onUpdate() {
    updatedAt = LocalDateTime.now();
  }
}

