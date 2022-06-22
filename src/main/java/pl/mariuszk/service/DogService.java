package pl.mariuszk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mariuszk.model.entity.DogEntity;
import pl.mariuszk.repository.DogRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DogService {

	private final DogRepository dogRepository;

	public void saveOrUpdateDogData(DogEntity dogEntity) {
		dogRepository.save(dogEntity);
	}

	public Optional<DogEntity> findByOwnerId(Long userId) {
		return dogRepository.findByOwnerId(userId);
	}

	public DogEntity findByOwnerIdOrThrow(Long userId) {
		return dogRepository.findByOwnerId(userId)
				.orElseThrow(() -> new IllegalStateException("Dog not found for user id: " + userId));
	}
}
