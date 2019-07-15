package ns.boot.jpa.utils.jpa;

import ns.boot.jpa.utils.jpa.annotations.QueryLimit;
import ns.boot.jpa.utils.jpa.annotations.QueryOrderDire;
import ns.boot.jpa.utils.jpa.annotations.QueryPage;
import ns.boot.jpa.utils.jpa.annotations.QueryType;
import ns.boot.jpa.utils.jpa.entity.QueryJoin;
import ns.boot.jpa.utils.jpa.entity.QueryOrder;
import ns.boot.jpa.utils.jpa.entity.QueryFilter;
import ns.boot.jpa.utils.jpa.enums.JoinParams;
import ns.boot.jpa.utils.jpa.enums.MatchType;
import ns.boot.jpa.utils.jpa.utils.QueryUtils;
import lombok.SneakyThrows;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author acer
 * @date 2018/7/30
 */
public class Query<T> implements Specification<T> {

	enum Condition {Or, And}

	private List<QueryFilter> andFilters = new ArrayList<>();
	private List<QueryFilter> orFilters = new ArrayList<>();
	private List<QueryJoin> joinFilters = new ArrayList<>();
	private List<QueryOrder> queryOrders = new ArrayList<>();
	private Map<String, Join> joinMap = new HashMap<>();
	private static Map<Enum, Method> parseMap = new HashMap<>();
	private static Map<Enum, Method> parseJoinMap = new HashMap<>();
	private Map<String, Object> queryInfo;

	static {
		initParseMap();
	}

	public Query<T> and(QueryFilter... queryFilters) {
		andFilters.addAll(Arrays.asList(queryFilters));
		return this;
	}

	public Query<T> or(QueryFilter... queryFilters) {
		orFilters.addAll(Arrays.asList(queryFilters));
		return this;
	}

	public Query<T> leftJoin(String tableName) {
		joinFilters.add(QueryJoin.leftJoin(tableName));
		return this;
	}

	public Query<T> leftListJoin(String tableName) {
		joinFilters.add(QueryJoin.leftListJoin(tableName));
		return this;
	}

	public Query<T> leftSetJoin(String tableName) {
		joinFilters.add(QueryJoin.leftSetJoin(tableName));
		return this;
	}

	public Query<T> leftMapJoin(String tableName) {
		joinFilters.add(QueryJoin.leftMapJoin(tableName));
		return this;
	}

	public Query<T> rightJoin(String tableName) {
		joinFilters.add(QueryJoin.rightJoin(tableName));
		return this;
	}

	public Query<T> rightListJoin(String tableName) {
		joinFilters.add(QueryJoin.rightListJoin(tableName));
		return this;
	}

	public Query<T> rightSetJoin(String tableName) {
		joinFilters.add(QueryJoin.rightSetJoin(tableName));
		return this;
	}

	public Query<T> rightMapJoin(String tableName) {
		joinFilters.add(QueryJoin.rightMapJoin(tableName));
		return this;
	}

	public Query<T> innerJoin(String tableName) {
		joinFilters.add(QueryJoin.innerJoin(tableName));
		return this;
	}

	public Query<T> innerListJoin(String tableName) {
		joinFilters.add(QueryJoin.innerListJoin(tableName));
		return this;
	}

	public Query<T> innerSetJoin(String tableName) {
		joinFilters.add(QueryJoin.innerSetJoin(tableName));
		return this;
	}

	public Query<T> innerMapJoin(String tableName) {
		joinFilters.add(QueryJoin.innerMapJoin(tableName));
		return this;
	}

	public Query<T> order(QueryOrder... orders) {
		queryOrders.addAll(Arrays.asList(orders));
		return this;
	}

	public Query<T> clearAllFilters() {
		clearAndFilters();
		clearOrFilters();
		return this;
	}

	public Query<T> clearAndFilters() {
		andFilters.clear();
		return this;
	}

	public Query<T> clearOrFilters() {
		orFilters.clear();
		return this;
	}

	public Query<T> clearOrders() {
		queryOrders.clear();
		return this;
	}

	public Query<T> addQueryInfo(Object o) {
		return this;
	}

	public Query<T> removeFilters(String... names) {
		for (String name : names) {
			andFilters.removeIf(af -> af.getName().equals(name));
			orFilters.removeIf(of -> of.getName().equals(name));
		}
		return this;
	}

	private void removeNullFilters() {
		andFilters.removeIf(af -> af.getName().equals(null) || af.getName().equals(""));
		orFilters.removeIf(of -> of.getName().equals(null) || of.getName().equals(""));
	}

	public Query(Object object) {
//		queryInfoObject = object;
//		getPageInfo(object);
//		buildQueryParams(object);
//		buildFilterValue(object);

		queryInfo = QueryUtils.objectMap(object);
	}

	public Query() {
	}

	@SneakyThrows
	private static void initParseMap() {
		for (MatchType types : MatchType.getAllTypes()) {
			if (types.getParamTypes().length == 0) {
				parseMap.put(types, types.getTargetClass().getMethod(types.getCbName(), types.getPathClass()));
			} else if (types.getParamTypes().length == 1) {
				parseMap.put(types, CriteriaBuilder.class.getMethod(types.getCbName(), types.getPathClass(), types.getParamTypes()[0]));
			} else if (types.getParamTypes().length == 2) {
				parseMap.put(types, CriteriaBuilder.class.getMethod(types.getCbName(), types.getPathClass(), types.getParamTypes()[0], types.getParamTypes()[1]));
			}
		}

		for (JoinParams joinParams : JoinParams.getAllJoinParams()) {
			parseJoinMap.put(joinParams, Root.class.getMethod(joinParams.getRootName(), String.class, JoinType.class));
		}
	}

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
		Predicate predicate = null;
		removeNullFilters();
//		criteriaQuery.multiselect(root.get("status"));
//		criteriaQuery.groupBy(root.get("status"));
		addJoin(joinFilters, root);
//		if (queryInfoObject != null) {
//			buildQueryParams(queryInfoObject);
//			buildFilterValue(queryInfoObject);
//		}
		//Order order = criteriaBuilder.asc(root.get("customer").get("balance"));
		//Order order1 = criteriaBuilder.asc(root.get("finalPrice"));
		//criteriaQuery.orderBy(order);//排序先后决定权值
		buildSort(root, criteriaQuery, criteriaBuilder);
		if (andFilters.size() != 0 && orFilters.size() == 0) {
			return parseFilters(andFilters, criteriaBuilder, root, predicate, Condition.And);
		} else if (andFilters.size() == 0 && orFilters.size() != 0) {
			return parseFilters(orFilters, criteriaBuilder, root, predicate, Condition.Or);
		} else if (andFilters.size() != 0 && orFilters.size() != 0) {
			return parseFilters(andFilters, criteriaBuilder, root, parseFilters(orFilters, criteriaBuilder, root, predicate, Condition.Or), Condition.And);
		} else {
			System.out.println("none predicate");
		}
		return predicate;
	}

	private Predicate chooseOrAnd(Predicate basicPredicate, Predicate newPredicate, CriteriaBuilder cb, Enum type) {
		return basicPredicate == null ?
				newPredicate :
				type == Condition.And ?
						cb.and(basicPredicate, newPredicate) :
						cb.or(basicPredicate, newPredicate);
	}

	@SneakyThrows
	private void addJoin(List<QueryJoin> queryJoins, Root<T> root) {
		for (QueryJoin queryJoin : queryJoins) {
			joinMap.put(queryJoin.getTable(), (Join) parseJoinMap.get(queryJoin.getJoinParams()).invoke(root, queryJoin.getTable(), queryJoin.getJoinType()));
		}
	}

	private Path buildPath(String paramsName, Root<T> root) {
		String[] params = paramsName.split("\\.");
		return build(params, 1, root.get(params[0]));
	}

	private Path build(String[] params, int i, Path path) {
		return params.length > i ? build(params, i + 1, path.get(params[i])) : path;
	}

	private void buildSort(Root<T> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
		queryOrders.forEach(order -> {
			if (order.getDirection().equals(Sort.Direction.ASC)) {
				cq.orderBy(cb.asc(buildPath(order.getName(), root)));
			} else {
				cq.orderBy(cb.desc(buildPath(order.getName(), root)));
			}
		});
	}

	private Join findJoin(Root root, String joinName) {
		return (Join) root.getJoins().stream().filter(join -> joinName.equals(((Join) join).getAlias()))
				.findAny()
				.orElse(null);
	}

	@SneakyThrows
	public Predicate buildPredicate(MatchType matchType, QueryFilter queryFilter, Root<T> root, CriteriaBuilder cb) {
		Method method = parseMap.get(matchType);
		if (matchType.equals(MatchType.IN)) {
			return (Predicate) method.invoke(buildPath(queryFilter.getName(), root), queryFilter.getValue());
		} else if (matchType.equals(MatchType.BETWEEN)) {
			return (Predicate) method.invoke(cb, buildPath(queryFilter.getName(), root), ((List) queryFilter.getValue()).get(0), ((List) queryFilter.getValue()).get(1));
		} else if (matchType.equals(MatchType.ISNOTNULL) || matchType.equals(MatchType.ISNULL)) {
			return (Predicate) method.invoke(cb, buildPath(queryFilter.getName(), root));
		} else {
			return (Predicate) method.invoke(cb, buildPath(queryFilter.getName(), root), queryFilter.getValue());
		}
	}

	private Predicate parseFilters(List<QueryFilter> queryParams, CriteriaBuilder cb, Root<T> root, Predicate predicate, Enum type) {
		for (QueryFilter queryFilter : queryParams) {
			predicate = chooseOrAnd(predicate, buildPredicate(queryFilter.getType(), queryFilter, root, cb), cb, type);
		}
		return predicate;
	}

	public void getPageInfo(Object object) {
		List<Field> fields = QueryUtils.getAllFields(object.getClass(), new ArrayList<Field>());
		List<String> orderDesc = new ArrayList<>();
		List<String> orderAsc = new ArrayList<>();

		for (Field field : fields) {
				QueryOrderDire queryOrder = field.getAnnotation(QueryOrderDire.class);

				if (queryOrder != null) {
					List<String> orders = (List<String>) QueryUtils.getValue(field.getName(), object);
					if (orders == null) continue;
					for (String order : orders) {
						List<String> oldOrders = queryOrders.stream()
								.map(orderFilter -> orderFilter.getName())
								.collect(Collectors.toList());
						if (!oldOrders.contains(order) && !QueryUtils.isEmpty(order)) {
							if (queryOrder.value() == Sort.Direction.DESC) {
								queryOrders.add(QueryOrder.desc(order));
							} else {
								queryOrders.add(QueryOrder.asc(order));
							}
						}
					}
				}
			}
	}

	public void buildFilterValue(Object object) {
		//根据filtername获取queryinfo的对应属性的值
		andFilters.forEach(queryFilter -> {
			queryFilter.setValue(QueryUtils.getValue(QueryUtils.getQueryFilterName(queryFilter.getName()), object));
		});
		orFilters.forEach(queryFilter -> {
			queryFilter.setValue(QueryUtils.getValue(QueryUtils.getQueryFilterName(queryFilter.getName()), object));
		});
	}

	public void buildQueryParams(Object object) {
		List<Field> fields = QueryUtils.getAllFields(object.getClass(), new ArrayList<Field>());
		for (Field field : fields) {
			QueryType queryPage = field.getAnnotation(QueryType.class);
			if (queryPage != null) {
				String name = field.getName();
				for (QueryJoin joinFilter : joinFilters) {
					String orderName = joinFilter.getTable();
					if (name.contains(orderName)) {
						String right = name.replaceAll(orderName, "");
						name = new StringBuilder(orderName)
								.append(".")
								.append(QueryUtils.changeFirstChar(right, QueryUtils.StringEnums.lower))
								.toString();
					}
				}
				andFilters.add(new QueryFilter(name, null, queryPage.value()));
			}
		}
	}

	public void autoJoin() {
		//base on queryinfo field auto join
	}

	public void buildQueryFilter() {
		if (queryInfo.containsKey("orderDesc")) {
			queryOrders.add(QueryOrder.desc(queryInfo.get("orderDesc").toString()));
		}
		if (queryInfo.containsKey("orderAsc")) {
			queryOrders.add(QueryOrder.desc(queryInfo.get("orderAsc").toString()));
		}

		queryInfo.forEach((k,v)-> {
			andFilters.add(QueryFilter.like(k, v.toString()));
		});
	}
}
