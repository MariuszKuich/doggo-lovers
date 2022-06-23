package pl.mariuszk.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Table(name = "PHOTOS")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhotoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PHOTO_ID")
	private Long id;

	@Lob
	@Type(type="org.hibernate.type.BinaryType")
	@Column(name = "IMAGE", nullable = false)
	private byte[] image;

	@Column(name = "CREATED_DATE", nullable = false)
	@CreationTimestamp
	private LocalDateTime createdDate;

	@OneToOne
	@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
	private UserEntity user;
}
