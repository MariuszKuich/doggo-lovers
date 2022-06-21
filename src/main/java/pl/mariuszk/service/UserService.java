package pl.mariuszk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.mariuszk.repository.UserRepository;
import pl.mariuszk.model.entity.UserEntity;
import pl.mariuszk.model.frontend.UserDto;

@Service
@RequiredArgsConstructor
public class UserService {

	private final PasswordEncoder encoder;
	private final UserRepository userRepository;

	public void registerNewUser(UserDto userDto) {
		if (usernameExists(userDto.getUsername())) {
			throw new IllegalStateException("Username already exists");
		}

		UserEntity userEntity = UserEntity.builder()
				.username(userDto.getUsername())
				.password(encoder.encode(userDto.getPassword()))
				.build();

		userRepository.save(userEntity);
	}

	private boolean usernameExists(String username) {
		return userRepository.findByUsername(username).isPresent();
	}

	public void registerNewOAuthUserIfNecessary(String username) {
		if (usernameExists(username)) {
			return;
		}

		UserEntity userEntity = UserEntity.builder()
				.username(username)
				.build();

		userRepository.save(userEntity);
	}
}
