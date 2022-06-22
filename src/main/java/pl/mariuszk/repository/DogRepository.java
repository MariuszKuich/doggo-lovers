package pl.mariuszk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mariuszk.model.entity.DogEntity;

import java.util.Optional;

public interface DogRepository extends JpaRepository<DogEntity, Long> {

	Optional<DogEntity> findByOwnerId(Long ownerId);
}
