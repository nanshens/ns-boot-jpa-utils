package ns.boot.jpa.utils.queryinfo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ns.boot.jpa.utils.entity.Address;
import ns.boot.jpa.utils.entity.Sex;

/**
 * @author ns
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerQueryInfo extends BaseQueryInfo{
	private String name;
	private Integer age;
	private Sex sex;
	private Address address;


}
