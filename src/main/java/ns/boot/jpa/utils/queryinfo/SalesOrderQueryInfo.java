package ns.boot.jpa.utils.queryinfo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ns.boot.jpa.utils.entity.Address;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
public class SalesOrderQueryInfo extends BaseQueryInfo{
	private String customer;
	private Address address;
	private Boolean paid;
	private Integer num;
}
