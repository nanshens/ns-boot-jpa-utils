package ns.boot.jpa.utils.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ns.boot.jpa.utils.jpa.enums.QueryMatchType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static ns.boot.jpa.utils.jpa.utils.QueryUtils.isNullOrEmpty;


/**
 * @author acer
 * @date 2018/7/30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public  class QueryParamsFilter {

    private String name;
    private Object value;
    private QueryMatchType type;

    public static QueryParamsFilter eq(String name, Object value){
        return new QueryParamsFilter(name, value, QueryMatchType.EQ);
    }

    public static QueryParamsFilter eq(String name){
        return new QueryParamsFilter(name, null, QueryMatchType.EQ);
    }

    public static QueryParamsFilter ne(String name, Object value){
        return new QueryParamsFilter(name, value, QueryMatchType.NE);
    }

    public static QueryParamsFilter ne(String name){
        return new QueryParamsFilter(name, null, QueryMatchType.NE);
    }

    public static QueryParamsFilter gt(String name, Object value){
        return new QueryParamsFilter(name, value, QueryMatchType.GT);
    }

    public static QueryParamsFilter gt(String name){
        return new QueryParamsFilter(name, null, QueryMatchType.GT);
    }

    public static QueryParamsFilter lt(String name, Object value){
        return new QueryParamsFilter(name, value, QueryMatchType.LT);
    }

    public static QueryParamsFilter lt(String name){
        return new QueryParamsFilter(name, null, QueryMatchType.LT);
    }

    public static QueryParamsFilter ge(String name, Object value){
        return new QueryParamsFilter(name, value, QueryMatchType.GE);
    }

    public static QueryParamsFilter ge(String name){
        return new QueryParamsFilter(name, null, QueryMatchType.GE);
    }

    public static QueryParamsFilter le(String name, Object value){
        return new QueryParamsFilter(name, value, QueryMatchType.LE);
    }
    public static QueryParamsFilter le(String name){
        return new QueryParamsFilter(name, null, QueryMatchType.LE);
    }

    public static QueryParamsFilter like(String name, String value){
        if (isNullOrEmpty(value)){
            value = "";
        }
        return new QueryParamsFilter(name, "%"+value+"%", QueryMatchType.LIKE);
    }

    public static QueryParamsFilter like(String name){
        return new QueryParamsFilter(name, null, QueryMatchType.LIKE);
    }

    public static QueryParamsFilter in(String name, Object... valueList){
        List values = new ArrayList();

        if (valueList.length > 1) {
            values.add(Arrays.asList(valueList));
        } else if (valueList.length == 1) {
            if (valueList[0] instanceof Collection){
                values = (List) valueList[0];
            } else if (valueList[0] instanceof Object[]) {
                values.add(Arrays.asList(valueList[0]));
            }else {
                values.add(valueList[0]);
            }
        }
        return new QueryParamsFilter(name, values, QueryMatchType.IN);
    }

    public static QueryParamsFilter in(String name){
        return new QueryParamsFilter(name, null, QueryMatchType.IN);
    }

    public static QueryParamsFilter isNull(String name){
        return new QueryParamsFilter(name,null, QueryMatchType.ISNULL);
    }

    public static QueryParamsFilter isNotNull(String name){
        return new QueryParamsFilter(name, null, QueryMatchType.ISNOTNULL);
    }

    public static <T extends Comparable> QueryParamsFilter between(String name, T minValue, T maxValue){
        List<Comparable> valueList = new ArrayList<>();
        valueList.add(minValue);
        valueList.add(maxValue);
        return new QueryParamsFilter(name, valueList, QueryMatchType.BETWEEN);
    }

    public static QueryParamsFilter between(String name){
        return new QueryParamsFilter(name, null, QueryMatchType.BETWEEN);
    }
}
