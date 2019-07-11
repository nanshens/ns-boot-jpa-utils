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
public class Item extends BaseEntity{
	private String name;
	private double cost;
}
