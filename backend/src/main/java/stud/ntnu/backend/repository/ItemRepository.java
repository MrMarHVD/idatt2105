package stud.ntnu.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stud.ntnu.backend.model.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    // No additional methods needed - we'll use the inherited findAll()
}