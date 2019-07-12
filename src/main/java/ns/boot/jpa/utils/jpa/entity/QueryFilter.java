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
public  class QueryFilter {

    private String name;
    private Object value;
    private QueryMatchType type;

    public static QueryFilter eq(String name, Object value){
        return new QueryFilter(name, value, QueryMatchType.EQ);
    }

    public static QueryFilter eq(String name){
        return new QueryFilter(name, null, QueryMatchType.EQ);
    }

    public static QueryFilter ne(String name, Object value){
        return new QueryFilter(name, value, QueryMatchType.NE);
    }

    public static QueryFilter ne(String name){
        return new QueryFilter(name, null, QueryMatchType.NE);
    }

    public static QueryFilter gt(String name, Object value){
        return new QueryFilter(name, value, QueryMatchType.GT);
    }

    public static QueryFilter gt(String name){
        return new QueryFilter(name, null, QueryMatchType.GT);
    }

    public static QueryFilter lt(String name, Object value){
        return new QueryFilter(name, value, QueryMatchType.LT);
    }

    public static QueryFilter lt(String name){
        return new QueryFilter(name, null, QueryMatchType.LT);
    }

    public static QueryFilter ge(String name, Object value){
        return new QueryFilter(name, value, QueryMatchType.GE);
    }

    public static QueryFilter ge(String name){
        return new QueryFilter(name, null, QueryMatchType.GE);
    }

    public static QueryFilter le(String name, Object value){
        return new QueryFilter(name, value, QueryMatchType.LE);
    }
    public static QueryFilter le(String name){
        return new QueryFilter(name, null, QueryMatchType.LE);
    }

    public static QueryFilter like(String name, String value){
        if (isNullOrEmpty(value)){
            value = "";
        }
        return new QueryFilter(name, "%"+value+"%", QueryMatchType.LIKE);
    }

    public static QueryFilter like(String name){
        return new QueryFilter(name, null, QueryMatchType.LIKE);
    }

    public static QueryFilter in(String name, Object... valueList){
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
        return new QueryFilter(name, values, QueryMatchType.IN);
    }

    public static QueryFilter in(String name){
        return new QueryFilter(name, null, QueryMatchType.IN);
    }

    public static QueryFilter isNull(String name){
        return new QueryFilter(name,null, QueryMatchType.ISNULL);
    }

    public static QueryFilter isNotNull(String name){
        return new QueryFilter(name, null, QueryMatchType.ISNOTNULL);
    }

    public static <T extends Comparable> QueryFilter between(String name, T minValue, T maxValue){
        List<Comparable> valueList = new ArrayList<>();
        valueList.add(minValue);
        valueList.add(maxValue);
        return new QueryFilter(name, valueList, QueryMatchType.BETWEEN);
    }

    public static QueryFilter between(String name){
        return new QueryFilter(name, null, QueryMatchType.BETWEEN);
    }
}
