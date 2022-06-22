package pl.mariuszk.model.frontend;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mariuszk.model.entity.UserEntity;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
public class WalkDto {
	@NotNull
	private UserEntity assignee;

	@NotNull
	private LocalDate walkDate;

	@NotNull
	private LocalTime walkTime;
}
