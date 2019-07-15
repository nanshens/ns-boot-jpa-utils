package ns.boot.jpa.utils.jpa;

import ns.boot.jpa.utils.entity.Address;
import ns.boot.jpa.utils.entity.Customer;
import ns.boot.jpa.utils.jpa.utils.QueryUtils;
import ns.boot.jpa.utils.queryinfo.SalesOrderQueryInfo;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author acer
 * @date 2018/7/30
 */
public class TestQuery<T> implements Specification<T> {

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

		Predicate predicate = null;
		predicate = criteriaBuilder.equal(buildPath("customer.address.code", root), "1");
		return predicate;
	}
	private Path buildPath(String paramsName, Root<T> root) {
		String[] params = paramsName.split("\\.");
		return build(params, 1, root.get(params[0]));
	}

	private Path build(String[] params, int i, Path path) {
		return params.length > i ? build(params, i + 1, path.get(params[i])) : path;
	}

	public static void main(String[] args) {
		SalesOrderQueryInfo queryInfo = new SalesOrderQueryInfo();
//		List<Field> fields = QueryUtils.getAllFields(queryInfo.getClass(), new ArrayList<>());
		Address a = new Address();
		a.setCity("1");
		a.setId("1");
		queryInfo.setCustomer("1");
		queryInfo.setAddress(a);
		queryInfo.setNum(1);
		queryInfo.setId("123");
		queryInfo.setCreateBy("zn");
//		Map<String, Object> field11 = QueryUtils.getAllFields(queryInfo.getClass(), new HashMap<>(), "");
//		field11.forEach((k,v) -> System.out.println(k + "  " + v));
		Map map = QueryUtils.objectMap(queryInfo);
		map.forEach((k,v)-> System.out.println(k +" "+ v));
	}


}
