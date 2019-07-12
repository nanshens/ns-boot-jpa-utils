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
public class QueryOrder {

    private String name;
    private QueryOrderDirection direction = QueryOrderDirection.Desc;
//    private Sort.Direction d= Sort.Direction.ASC;



    public static QueryOrder desc(String name) {
        return new QueryOrder(name, QueryOrderDirection.Desc);
    }

    public static QueryOrder asc(String name) {
        return new QueryOrder(name, QueryOrderDirection.Asc);
    }

}
