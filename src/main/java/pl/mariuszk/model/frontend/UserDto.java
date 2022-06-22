package pl.mariuszk.model.frontend;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class UserDto {

	@NotEmpty
	@Length(min = 3, max = 30)
	@Email
	private String username;

	@NotEmpty
	@Length(min = 8, max = 20)
	private String password;

	@NotEmpty
	@Length(min = 3, max = 20)
	private String name;
}
