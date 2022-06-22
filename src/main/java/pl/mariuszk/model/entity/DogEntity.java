package pl.mariuszk.model.entity;

import lombok.Data;
import pl.mariuszk.enums.Breed;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Table(name = "DOGS")
@Entity
@Data
public class DogEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DOG_ID")
	private Long id;

	@Column(name = "NAME", nullable = false)
	@NotEmpty
	@Size(min = 3, max = 20)
	private String name;

	@Column(name = "BREED")
	@Enumerated(EnumType.STRING)
	@NotNull
	private Breed breed;

	@Column(name = "DATE_OF_BIRTH")
	@NotNull
	private LocalDate dateOfBirth;

	@OneToOne
	@JoinColumn(name = "OWNER_ID", referencedColumnName = "USER_ID")
	private UserEntity owner;
}
