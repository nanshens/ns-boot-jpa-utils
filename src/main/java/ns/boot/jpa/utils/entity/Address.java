package ns.boot.jpa.utils.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author zn
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Address extends BaseEntity{
	private String city;
	private String phone;

	@ManyToOne
	private Customer customer;
}
