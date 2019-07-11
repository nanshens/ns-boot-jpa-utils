package ns.boot.jpa.utils.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.ManyToOne;

/**
 * @author zn
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SalesOrder extends BaseEntity{

	@ManyToOne
	private Customer customer;
	@ManyToOne
	private Item item;
	private double number;
	private double totalCost;
	private Status status;
	private boolean paid;

}
