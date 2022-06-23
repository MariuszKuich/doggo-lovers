package pl.mariuszk.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
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

@Table(name = "VET_HISTORY")
@Entity
@Data
public class VetHistoryEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "HIST_ID")
	private Long id;

	@Column(name = "VISIT_DATE", nullable = false)
	@NotNull
	private LocalDate visitDate;

	@Column(name = "CAUSE", nullable = false)
	@NotEmpty
	@Size(min = 3, max = 100)
	private String cause;

	@Column(name = "RECOMMENDATIONS", nullable = false)
	@NotEmpty
	@Size(min = 3, max = 500)
	private String recommendations;

	@OneToOne
	@JoinColumn(name = "DOG_ID", referencedColumnName = "DOG_ID")
	private DogEntity dog;
}
