package pl.mariuszk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mariuszk.model.entity.VetHistoryEntity;

import java.util.List;

@Repository
public interface VetHistoryRepository extends JpaRepository<VetHistoryEntity, Long> {

	List<VetHistoryEntity> findByDogIdOrderByVisitDateDescIdDesc(Long dogId);
}
