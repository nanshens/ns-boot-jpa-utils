package ns.boot.jpa.utils.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.List;

/**
 * @author zn
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Job extends BaseEntity{
	private String name;

	@ManyToMany
//	@JoinTable(name = "user_role")
	private List<Employee> employees;
}
