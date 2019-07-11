package ns.boot.jpa.utils.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

/**
 * @author zn
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Employee extends BaseEntity{
	private String name;
}
