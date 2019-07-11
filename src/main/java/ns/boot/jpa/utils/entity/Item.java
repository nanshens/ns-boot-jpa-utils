package ns.boot.jpa.utils.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zn
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Item extends BaseEntity{
	private String name;
	private double cost;
}
