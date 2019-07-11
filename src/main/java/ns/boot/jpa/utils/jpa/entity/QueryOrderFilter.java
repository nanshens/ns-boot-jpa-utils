package ns.boot.jpa.utils.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ns.boot.jpa.utils.jpa.enums.QueryOrderDirection;

/**
 * @author zn
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryOrderFilter {

    private String name;
    private QueryOrderDirection direction = QueryOrderDirection.Desc;
//    private Sort.Direction d= Sort.Direction.ASC;



    public static QueryOrderFilter desc(String name) {
        return new QueryOrderFilter(name, QueryOrderDirection.Desc);
    }

    public static QueryOrderFilter asc(String name) {
        return new QueryOrderFilter(name, QueryOrderDirection.Asc);
    }

}
