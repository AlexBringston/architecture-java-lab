package application.repositories;

import application.model.Category;
import application.model.Illness;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IllnessRepository extends JpaRepository<Illness, Long> {

    Long countIllnessByCategory(Category category);

}
