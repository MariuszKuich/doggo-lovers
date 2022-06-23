package pl.mariuszk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mariuszk.model.entity.PhotoEntity;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<PhotoEntity, Long> {

	List<PhotoEntity> findByOrderByCreatedDateDesc();
}
