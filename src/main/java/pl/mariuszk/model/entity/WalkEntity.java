package pl.mariuszk.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Table(name = "WALKS")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WalkEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "WALK_ID")
	private Long id;

	@OneToOne
	@JoinColumn(name = "ASSIGNER_ID", referencedColumnName = "USER_ID", nullable = false)
	private UserEntity assigner;

	@OneToOne
	@JoinColumn(name = "ASSIGNEE_ID", referencedColumnName = "USER_ID", nullable = false)
	private UserEntity assignee;

	@OneToOne
	@JoinColumn(name = "DOG_ID", referencedColumnName = "DOG_ID", nullable = false)
	private DogEntity dog;

	@Column(name = "WALK_DATE", nullable = false)
	private LocalDateTime walkDate;

	@Column(name = "CREATED_DATE", nullable = false)
	@CreationTimestamp
	private LocalDateTime createdDate;

	@Column(name = "CONFIRMED", nullable = false)
	private boolean confirmed;
}
