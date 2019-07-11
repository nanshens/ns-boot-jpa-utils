package ns.boot.jpa.utils.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;

/**
 * @author zn
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Customer extends BaseEntity{
	private String name;
	private Integer age;
	@Enumerated(EnumType.STRING)
	private Sex sex;

}
