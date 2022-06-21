package pl.mariuszk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mariuszk.model.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	Optional<UserEntity> findByUsername(String username);
}
