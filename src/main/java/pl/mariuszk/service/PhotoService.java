package pl.mariuszk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mariuszk.model.entity.PhotoEntity;
import pl.mariuszk.repository.PhotoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PhotoService {

	private final PhotoRepository photoRepository;

	public void save(PhotoEntity photoEntity) {
		photoRepository.save(photoEntity);
	}

	public List<PhotoEntity> getAllPhotos() {
		return photoRepository.findByOrderByCreatedDateDesc();
	}
}
