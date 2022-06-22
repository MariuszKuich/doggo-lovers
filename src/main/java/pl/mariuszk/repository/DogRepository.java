package pl.mariuszk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mariuszk.model.entity.DogEntity;

import java.util.Optional;

@Repository
public interface DogRepository extends JpaRepository<DogEntity, Long> {

	Optional<DogEntity> findByOwnerId(Long ownerId);
}
