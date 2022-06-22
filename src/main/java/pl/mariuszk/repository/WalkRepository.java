package pl.mariuszk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mariuszk.model.entity.WalkEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface WalkRepository extends JpaRepository<WalkEntity, Long> {

	List<WalkEntity> findByAssignerIdOrderByWalkDateDesc(Long assignerId);

	List<WalkEntity> findByAssigneeIdOrderByWalkDateDesc(Long assigneeId);

	Optional<WalkEntity> findByDogIdAndWalkDate(Long dogId, LocalDateTime walkDate);
	Optional<WalkEntity> findByAssigneeIdAndWalkDate(Long assigneeId, LocalDateTime walkDate);
}
