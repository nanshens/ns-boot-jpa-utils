package ns.boot.jpa.utils.jpa;

import ns.boot.jpa.utils.entity.Address;
import ns.boot.jpa.utils.entity.Customer;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


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

}
