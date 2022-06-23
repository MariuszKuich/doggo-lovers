package pl.mariuszk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mariuszk.model.entity.VetHistoryEntity;
import pl.mariuszk.repository.VetHistoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VetHistoryService {

	private final VetHistoryRepository vetHistoryRepository;

	public void addVisit(VetHistoryEntity vetHistoryEntity) {
		vetHistoryRepository.save(vetHistoryEntity);
	}

	public List<VetHistoryEntity> getDogsVisits(Long dogId) {
		return vetHistoryRepository.findByDogIdOrderByVisitDateDescIdDesc(dogId);
	}
}
