package ns.boot.jpa.utils.jpa;

import ns.boot.jpa.utils.jpa.annotations.QueryInfo;
import ns.boot.jpa.utils.jpa.annotations.QueryLimit;
import ns.boot.jpa.utils.jpa.annotations.QueryOrder;
import ns.boot.jpa.utils.jpa.annotations.QueryPage;
import ns.boot.jpa.utils.jpa.annotations.QueryType;
import ns.boot.jpa.utils.jpa.entity.QueryJoinFilter;
import ns.boot.jpa.utils.jpa.entity.QueryOrderFilter;
import ns.boot.jpa.utils.jpa.entity.QueryParamsFilter;
import ns.boot.jpa.utils.jpa.enums.JoinParams;
import ns.boot.jpa.utils.jpa.enums.QueryMatchType;
import ns.boot.jpa.utils.jpa.enums.QueryOrderDirection;
import ns.boot.jpa.utils.jpa.utils.QueryUtils;
import lombok.SneakyThrows;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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

	private List<QueryParamsFilter> andFilters = new ArrayList<>();
	private List<QueryParamsFilter> orFilters = new ArrayList<>();
	private List<QueryJoinFilter> joinFilters = new ArrayList<>();
	private List<QueryOrderFilter> orderFilters = new ArrayList<>();
	private Map<String, Join> joinMap = new HashMap<>();
	private static Map<Enum, Method> parseMap = new HashMap<>();
	private static Map<Enum, Method> parseJoinMap = new HashMap<>();
	private int page = 0;
	private int limit = 0;
	private Object queryInfoObject;

	static{
		initParseMap();
	}

	public Query<T> and(QueryParamsFilter... queryParamsFilters) {
		andFilters.addAll(Arrays.asList(queryParamsFilters));
		return this;
	}

	public Query<T> or(QueryParamsFilter... queryParamsFilters) {
		orFilters.addAll(Arrays.asList(queryParamsFilters));
		return this;
	}

	public Query<T> leftJoin(String tableName) {
		joinFilters.add(QueryJoinFilter.leftJoin(tableName));
		return this;
	}

	public Query<T> leftListJoin(String tableName) {
		joinFilters.add(QueryJoinFilter.leftListJoin(tableName));
		return this;
	}

	public Query<T> leftSetJoin(String tableName) {
		joinFilters.add(QueryJoinFilter.leftSetJoin(tableName));
		return this;
	}

	public Query<T> leftMapJoin(String tableName) {
		joinFilters.add(QueryJoinFilter.leftMapJoin(tableName));
		return this;
	}

	public Query<T> rightJoin(String tableName) {
		joinFilters.add(QueryJoinFilter.rightJoin(tableName));
		return this;
	}

	public Query<T> rightListJoin(String tableName) {
		joinFilters.add(QueryJoinFilter.rightListJoin(tableName));
		return this;
	}

	public Query<T> rightSetJoin(String tableName) {
		joinFilters.add(QueryJoinFilter.rightSetJoin(tableName));
		return this;
	}

	public Query<T> rightMapJoin(String tableName) {
		joinFilters.add(QueryJoinFilter.rightMapJoin(tableName));
		return this;
	}

	public Query<T> innerJoin(String tableName) {
		joinFilters.add(QueryJoinFilter.innerJoin(tableName));
		return this;
	}

	public Query<T> innerListJoin(String tableName) {
		joinFilters.add(QueryJoinFilter.innerListJoin(tableName));
		return this;
	}

	public Query<T> innerSetJoin(String tableName) {
		joinFilters.add(QueryJoinFilter.innerSetJoin(tableName));
		return this;
	}

	public Query<T> innerMapJoin(String tableName) {
		joinFilters.add(QueryJoinFilter.innerMapJoin(tableName));
		return this;
	}

	public Query<T> order(QueryOrderFilter... orders) {
		orderFilters.addAll(Arrays.asList(orders));
		return this;
	}

	public Query<T> delListFilter(String name) {
		//container orlist andlist orderlist joinlist need improve
		return this;
	}

	public Query<T> changeListFilter(String name) {
		return this;
	}

	public void page() {

	}

	public Query(Object object) {
		queryInfoObject = object;
		QueryInfo queryInfo = object.getClass().getAnnotation(QueryInfo.class);
		if (queryInfo != null && object != null) {
			getPageInfo(object);
//			buildQueryParams(object);
//			buildFilterValue(object);
		}

	}

	public Query() {}

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

		//predicate = criteriaBuilder.equal(root.get("_C_").get("name"),"11");
		Predicate predicate = null;

		//ListJoin roles = root.joinList("roles", JoinType.LEFT);

		//predicate = roles.get("code").in("admin");
		//delete filter if value = null
		//removeNullQueryParams();
//		criteriaQuery.multiselect(root.get("status"));
//		criteriaQuery.groupBy(root.get("status"));
		predicate = criteriaBuilder.greaterThan(root.get("code"), "1");
		try {
			addJoin(joinFilters, root);
			if (queryInfoObject != null) {
				buildQueryParams(queryInfoObject);
				buildFilterValue(queryInfoObject);
			}
		} catch (InvocationTargetException | IllegalAccessException e) {
			e.printStackTrace();
		}
		//Order order = criteriaBuilder.asc(root.get("customer").get("balance"));
		//Order order1 = criteriaBuilder.asc(root.get("finalPrice"));
		//criteriaQuery.orderBy(order);//排序先后决定权值
		buildSort(root, criteriaQuery, criteriaBuilder);
		if (andFilters.size() != 0 && orFilters.size() == 0) {
			return parseFilters1(andFilters, criteriaBuilder, root, predicate, Condition.And);
		} else if (andFilters.size() == 0 && orFilters.size() != 0) {
			return parseFilters1(orFilters, criteriaBuilder, root, predicate, Condition.Or);
		} else if (andFilters.size() != 0 && orFilters.size() != 0) {
			return parseFilters1(andFilters, criteriaBuilder, root, parseFilters(orFilters, criteriaBuilder, root, predicate, Condition.Or), Condition.And);
		} else {
			System.out.println("none predicate");
		}
		return predicate;
	}

	private Predicate parseFilters(List<QueryParamsFilter> queryParams, CriteriaBuilder criteriaBuilder, Root<T> root, Predicate predicate, Enum type) {
		if (queryParams != null) {
			for (QueryParamsFilter queryParamsFilter : queryParams) {
				switch (queryParamsFilter.getType()) {
					case EQ:
						predicate = chooseOrAnd(predicate, criteriaBuilder.equal(analyzeParamsName(queryParamsFilter.getName(), root), queryParamsFilter.getValue()), criteriaBuilder, type);
						break;
					case NE:
						predicate = chooseOrAnd(predicate, criteriaBuilder.notEqual(analyzeParamsName(queryParamsFilter.getName(), root), queryParamsFilter.getValue()), criteriaBuilder, type);
						break;
					case GT:
						predicate = chooseOrAnd(predicate, criteriaBuilder.gt(analyzeParamsName(queryParamsFilter.getName(), root), (Number) queryParamsFilter.getValue()), criteriaBuilder, type);
						break;
					case LT:
						predicate = chooseOrAnd(predicate, criteriaBuilder.lt(analyzeParamsName(queryParamsFilter.getName(), root), (Number) queryParamsFilter.getValue()), criteriaBuilder, type);
						break;
					case GE:
						predicate = chooseOrAnd(predicate, criteriaBuilder.ge(analyzeParamsName(queryParamsFilter.getName(), root), (Number) queryParamsFilter.getValue()), criteriaBuilder, type);
						break;
					case LE:
						predicate = chooseOrAnd(predicate, criteriaBuilder.le(analyzeParamsName(queryParamsFilter.getName(), root), (Number) queryParamsFilter.getValue()), criteriaBuilder, type);
						break;
					case LIKE:
						predicate = chooseOrAnd(predicate, criteriaBuilder.like(analyzeParamsName(queryParamsFilter.getName(), root), (String) queryParamsFilter.getValue()), criteriaBuilder, type);
						break;
					case IN:
//						predicate = chooseOrAnd(predicate, root.get(queryParamsFilter.getName()).in(queryParamsFilter.getValue()), criteriaBuilder, type);
						predicate = chooseOrAnd(predicate, analyzeParamsName(queryParamsFilter.getName(), root).in(queryParamsFilter.getValue()), criteriaBuilder, type);
						break;
					case ISNULL:
						predicate = chooseOrAnd(predicate, criteriaBuilder.isNull(analyzeParamsName(queryParamsFilter.getName(), root)), criteriaBuilder, type);
						break;
					case ISNOTNULL:
						predicate = chooseOrAnd(predicate, criteriaBuilder.isNotNull(analyzeParamsName(queryParamsFilter.getName(), root)), criteriaBuilder, type);
						break;
					case BETWEEN:
						predicate = chooseOrAnd(predicate, criteriaBuilder.between(analyzeParamsName(queryParamsFilter.getName(), root), (Comparable) (((List) queryParamsFilter.getValue()).get(0)), (Comparable) (((List) queryParamsFilter.getValue()).get(1))), criteriaBuilder, type);
						break;
					default:
				}
			}
		}
		return predicate;
	}

	private Predicate chooseOrAnd(Predicate basicPredicate, Predicate newPredicate, CriteriaBuilder criteriaBuilder, Enum type) {
		return basicPredicate == null ?
				newPredicate : type == Condition.And ? criteriaBuilder.and(basicPredicate, newPredicate) : criteriaBuilder.or(basicPredicate, newPredicate);
	}

	private void addJoin(List<QueryJoinFilter> queryJoinFilters, Root<T> root) throws InvocationTargetException, IllegalAccessException {
		for (QueryJoinFilter queryJoinFilter : queryJoinFilters) {
			joinMap.put(queryJoinFilter.getTable(), (Join) parseJoinMap.get(queryJoinFilter.getJoinParams()).invoke(root, queryJoinFilter.getTable(), queryJoinFilter.getJoinType()));
//			switch (queryJoinFilter.getJoinParams()) {
//				case List:
//					joinMap.put(queryJoinFilter.getTable(), root.joinList(queryJoinFilter.getTable(), queryJoinFilter.getJoinType()));
//					break;
//				case Map:
//					joinMap.put(queryJoinFilter.getTable(), root.joinMap(queryJoinFilter.getTable(), queryJoinFilter.getJoinType()));
//					break;
//				case Set:
//					joinMap.put(queryJoinFilter.getTable(), root.joinSet(queryJoinFilter.getTable(), queryJoinFilter.getJoinType()));
//					break;
//				case Default:
//					joinMap.put(queryJoinFilter.getTable(), root.join(queryJoinFilter.getTable(), queryJoinFilter.getJoinType()));
//					break;
//				default:
//			}
		}
	}

	private Path analyzeParamsName(String paramsName, Root<T> root) {
		//解析join表的name属性 长度是1表示根节点的条件, 长度是2 表示join表的条件 表名加上字段名
		String[] params = paramsName.split("\\.");
		return params.length == 1 ? root.get(params[0]) : joinMap.get(params[0]).get(params[1]);
	}

	private void buildSort(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
		orderFilters.forEach(order -> {
			if (order.getDirection().equals(QueryOrderDirection.Asc)) {
				criteriaQuery.orderBy(criteriaBuilder.asc(analyzeParamsName(order.getName(), root)));
			} else {
				criteriaQuery.orderBy(criteriaBuilder.desc(analyzeParamsName(order.getName(), root)));
			}
		});
	}

	private Join findJoin(Root root, String joinName) {
		return (Join) root.getJoins().stream().filter(join -> joinName.equals(((Join) join).getAlias()))
				.findAny()
				.orElse(null);
	}

	private void removeNullQueryParams() {

	}

	@SneakyThrows
	private static void initParseMap(){
		//try add some params in enums and invoke get
		QueryMatchType.getAllTypes()
				.stream()
				.forEach(queryMatchType -> {
					try {
						if (queryMatchType.getParamTypes().length == 0) {
							parseMap.put(queryMatchType, queryMatchType.getTargetClass().getMethod(queryMatchType.getCbName(), queryMatchType.getPathClass()));
						}else if (queryMatchType.getParamTypes().length == 1) {
							parseMap.put(queryMatchType, CriteriaBuilder.class.getMethod(queryMatchType.getCbName(), queryMatchType.getPathClass(), queryMatchType.getParamTypes()[0]));
						}else if (queryMatchType.getParamTypes().length == 2) {
							parseMap.put(queryMatchType, CriteriaBuilder.class.getMethod(queryMatchType.getCbName(), queryMatchType.getPathClass(), queryMatchType.getParamTypes()[0], queryMatchType.getParamTypes()[1]));
						}
					}catch (NoSuchMethodException e) {
						System.out.println("add parseMap error");
					}
				});


//		JoinParams.getAllJoinParams()
//				.stream()
//				.forEach(joinParams ->
//						QueryUtils.wrapException(() -> parseJoinMap.put(joinParams, Root.class.getMethod(joinParams.getRootName(), String.class, JoinType.class))));

		JoinParams.getAllJoinParams()
				.stream()
				.forEach(joinParams -> {
					try {
						parseJoinMap.put(joinParams, Root.class.getMethod(joinParams.getRootName(), String.class, JoinType.class));
					} catch (NoSuchMethodException e) {
						System.out.println("add joinmap error");
					}
				});

//		parseMap.put(QueryMatchType.EQ, CriteriaBuilder.class.getMethod("equal", Expression.class, Object.class));
//		parseMap.put(QueryMatchType.NE, CriteriaBuilder.class.getMethod("notEqual", Expression.class, Object.class));
//		parseMap.put(QueryMatchType.GT, CriteriaBuilder.class.getMethod("gt", Expression.class, Number.class));
//		parseMap.put(QueryMatchType.LT, CriteriaBuilder.class.getMethod("lt", Expression.class, Number.class));
//		parseMap.put(QueryMatchType.GE, CriteriaBuilder.class.getMethod("ge", Expression.class, Number.class));
//		parseMap.put(QueryMatchType.LE, CriteriaBuilder.class.getMethod("le", Expression.class, Number.class));
//		parseMap.put(QueryMatchType.LIKE, CriteriaBuilder.class.getMethod("like", Expression.class, String.class));
//		parseMap.put(QueryMatchType.IN, Path.class.getMethod("in", Collection.class));
//		parseMap.put(QueryMatchType.ISNOTNULL, CriteriaBuilder.class.getMethod("isNotNull", Expression.class));
//		parseMap.put(QueryMatchType.ISNULL, CriteriaBuilder.class.getMethod("isNull", Expression.class));
//		parseMap.put(QueryMatchType.BETWEEN, CriteriaBuilder.class.getMethod("between", Expression.class, Comparable.class, Comparable.class));

//		parseJoinMap.put(JoinParams.Default, Root.class.getMethod("join", String.class, JoinType.class));
//		parseJoinMap.put(JoinParams.List, Root.class.getMethod("joinList", String.class, JoinType.class));
//		parseJoinMap.put(JoinParams.Set, Root.class.getMethod("joinSet", String.class, JoinType.class));
//		parseJoinMap.put(JoinParams.Map, Root.class.getMethod("joinMap", String.class, JoinType.class));

	}

	private Predicate parseFilters1(List<QueryParamsFilter> queryParams, CriteriaBuilder criteriaBuilder, Root<T> root, Predicate predicate, Enum type) {
		for(QueryParamsFilter queryParamsFilter : queryParams) {
			try {
				if (!QueryUtils.isNullOrEmpty(queryParamsFilter.getValue())){
					QueryMatchType matchType = queryParamsFilter.getType();
					Method method = parseMap.get(matchType);
					Predicate newPredicate;
					if (matchType.equals(QueryMatchType.IN)) {
						newPredicate = (Predicate) method.invoke(analyzeParamsName(queryParamsFilter.getName(), root), queryParamsFilter.getValue());
					} else if (matchType.equals(QueryMatchType.BETWEEN)) {
						newPredicate = (Predicate) method.invoke(criteriaBuilder, analyzeParamsName(queryParamsFilter.getName(), root), ((List) queryParamsFilter.getValue()).get(0), ((List) queryParamsFilter.getValue()).get(1));
					} else if (matchType.equals(QueryMatchType.ISNOTNULL) || matchType .equals(QueryMatchType.ISNULL)) {
						newPredicate = (Predicate) method.invoke(criteriaBuilder, analyzeParamsName(queryParamsFilter.getName(), root));
					} else {
						newPredicate = (Predicate) method.invoke(criteriaBuilder, analyzeParamsName(queryParamsFilter.getName(), root), queryParamsFilter.getValue());
					}
					predicate = chooseOrAnd(predicate, newPredicate, criteriaBuilder, type);
				}
			} catch (IllegalAccessException | InvocationTargetException e) {
				System.out.println("IllegalAccessException | InvocationTargetException");
			}
		}
		return predicate;
	}

//	public Class<R> getPageInfoClass() {
//		Type type = getClass().getGenericSuperclass();
//		ParameterizedType parameterizedType = ParameterizedType.class.cast(type);
//		return (Class<R>) parameterizedType.getActualTypeArguments()[1];
//	}

	public static void main(String[] args) {
//		ArticleQueryInfo articleQueryInfo = new ArticleQueryInfo();
//		articleQueryInfo.setPage(2);
//		articleQueryInfo.setLimit(222);
//		articleQueryInfo.setOrderAsc(new ArrayList<String>() {{
//			add("id");add("name");
//		}});
//		articleQueryInfo.setOrderDesc(new ArrayList<String>() {{
//			add("name");add("id");
//		}});
//		Query<Article> queryInfoQuery = new Query<>(articleQueryInfo);

//		queryInfoQuery.getPageInfo();
	}

	public void getPageInfo(Object object) {
		List<Field> fields = QueryUtils.getAllFields(object.getClass(), new ArrayList());
		List<String> orderDesc = new ArrayList<>();
		List<String> orderAsc = new ArrayList<>();

		try {
			for (Field field : fields) {
				QueryPage queryPage = field.getAnnotation(QueryPage.class);
				QueryLimit queryLimit = field.getAnnotation(QueryLimit.class);
				QueryOrder queryOrder = field.getAnnotation(QueryOrder.class);

				if (queryPage != null) {
					page = (int) QueryUtils.getValue(field.getName(), object);
					continue;
				}

				if (queryLimit != null) {
					limit = (int) QueryUtils.getValue(field.getName(), object);
					continue;
				}
				if (queryOrder != null) {
					List<String> orders = (List<String>) QueryUtils.getValue(field.getName(), object);
					if (orders == null) continue;
					for (String order : orders) {
						List<String> oldOrders = orderFilters.stream()
								.map(orderFilter -> orderFilter.getName())
								.collect(Collectors.toList());
						if (!oldOrders.contains(order) && !QueryUtils.isEmpty(order)) {
							if (queryOrder.value() == QueryOrderDirection.Desc) {
								orderFilters.add(QueryOrderFilter.desc(order));
							} else {
								orderFilters.add(QueryOrderFilter.asc(order));
							}
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println("getPageInfo error");
		}

		System.out.println(page);
		System.out.println(limit);
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
		List<Field> fields = QueryUtils.getAllFields(object.getClass(), new ArrayList());
		for (Field field : fields) {
			QueryType queryPage = field.getAnnotation(QueryType.class);
			if (queryPage != null) {
				String name = field.getName();
				for (QueryJoinFilter joinFilter : joinFilters) {
					String orderName = joinFilter.getTable();
					if (name.contains(orderName)) {
						String right = name.replaceAll(orderName, "");
						name = new StringBuilder(orderName)
								.append(".")
								.append(QueryUtils.changeFirstChar(right, QueryUtils.StringEnums.lower))
								.toString();
					}
				}
				andFilters.add(new QueryParamsFilter(name,null, queryPage.value()));
			}
		}
	}

	public void autoJoin() {
		//base on queryinfo field auto join

	}
}
