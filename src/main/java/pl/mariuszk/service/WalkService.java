package pl.mariuszk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mariuszk.model.entity.DogEntity;
import pl.mariuszk.model.entity.UserEntity;
import pl.mariuszk.model.entity.WalkEntity;
import pl.mariuszk.model.frontend.WalkDto;
import pl.mariuszk.repository.WalkRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WalkService {

	private final WalkRepository walkRepository;

	public List<WalkEntity> getWalksAssignedByUser(Long userId) {
		return walkRepository.findByAssignerIdOrderByWalkDateDesc(userId);
	}

	public List<WalkEntity> getWalksAssignedToUser(Long userId) {
		return walkRepository.findByAssigneeIdOrderByWalkDateDesc(userId);
	}

	public boolean dogIsAlreadyWalkedAtGivenTime(Long dogId, LocalDate date, LocalTime time) {
		LocalDateTime dateTime = LocalDateTime.of(date, time);
		return walkRepository.findByDogIdAndWalkDate(dogId, dateTime).isPresent();
	}

	public boolean userHasWalkScheduledAtGivenTime(Long userId, LocalDate date, LocalTime time) {
		LocalDateTime dateTime = LocalDateTime.of(date, time);
		return walkRepository.findByAssigneeIdAndWalkDate(userId, dateTime).isPresent();
	}

	public void save(WalkDto walkDto, UserEntity userEntity, DogEntity dogEntity) {
		WalkEntity walkEntity = WalkEntity.builder()
				.assigner(userEntity)
				.assignee(walkDto.getAssignee())
				.dog(dogEntity)
				.walkDate(LocalDateTime.of(walkDto.getWalkDate(), walkDto.getWalkTime()))
				.confirmed(false)
				.build();

		walkRepository.save(walkEntity);
	}

	public void updateWalk(WalkEntity walkEntity) {
		walkRepository.save(walkEntity);
	}
}
