package pl.mariuszk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.mariuszk.repository.UserRepository;
import pl.mariuszk.model.entity.UserEntity;
import pl.mariuszk.model.frontend.UserDto;
import pl.mariuszk.security.CustomOAuthUserPrincipal;
import pl.mariuszk.security.ICustomUserDetails;
import pl.mariuszk.service.security.SecurityService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

	private final PasswordEncoder encoder;
	private final UserRepository userRepository;
	private final SecurityService securityService;

	public void registerNewUser(UserDto userDto) {
		if (usernameExists(userDto.getUsername())) {
			throw new IllegalStateException("Username already exists");
		}

		UserEntity userEntity = UserEntity.builder()
				.username(userDto.getUsername())
				.password(encoder.encode(userDto.getPassword()))
				.name(userDto.getName())
				.build();

		userRepository.save(userEntity);
	}

	private boolean usernameExists(String username) {
		return userRepository.findByUsername(username).isPresent();
	}

	public void registerNewOAuthUserIfNecessary(CustomOAuthUserPrincipal userPrincipal) {
		if (usernameExists(userPrincipal.getUsername())) {
			return;
		}

		UserEntity userEntity = UserEntity.builder()
				.username(userPrincipal.getUsername())
				.name(userPrincipal.getName())
				.build();

		userRepository.save(userEntity);
	}

	public UserEntity getUserByUsername(String username) {
		return userRepository.findByUsername(username)
				.orElseThrow(() -> new IllegalStateException("User" + username + " not found"));
	}

	public UserEntity getUserFromSecurityContext() {
		ICustomUserDetails loggedUser = securityService.getAuthenticatedUser();
		return getUserByUsername(loggedUser.getUsername());
	}

	public List<UserEntity> getAllUsersExcept(Long userId) {
		return userRepository.findByIdNotOrderByName(userId);
	}
}
