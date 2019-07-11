package ns.boot.jpa.utils.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ns.boot.jpa.utils.jpa.enums.JoinParams;

import javax.persistence.criteria.JoinType;

/**
 * @author zn
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryJoinFilter {
    private String table;
    private JoinType joinType;
    private JoinParams joinParams;

    public static QueryJoinFilter leftJoin(String table){
        return new QueryJoinFilter(table, JoinType.LEFT, JoinParams.Default);
    }

    public static QueryJoinFilter rightJoin(String table){
        return new QueryJoinFilter(table, JoinType.RIGHT, JoinParams.Default);
    }

    public static QueryJoinFilter innerJoin(String table){
        return new QueryJoinFilter(table, JoinType.INNER, JoinParams.Default);
    }

    public static QueryJoinFilter leftListJoin(String table) {
        return new QueryJoinFilter(table, JoinType.LEFT, JoinParams.List);
    }

    public static QueryJoinFilter leftSetJoin(String table) {
        return new QueryJoinFilter(table, JoinType.LEFT, JoinParams.Set);
    }

    public static QueryJoinFilter leftMapJoin(String table) {
        return new QueryJoinFilter(table, JoinType.LEFT, JoinParams.Map);
    }

    public static QueryJoinFilter rightListJoin(String table) {
        return new QueryJoinFilter(table, JoinType.RIGHT, JoinParams.List);
    }

    public static QueryJoinFilter rightSetJoin(String table) {
        return new QueryJoinFilter(table, JoinType.RIGHT, JoinParams.Set);
    }

    public static QueryJoinFilter rightMapJoin(String table) {
        return new QueryJoinFilter(table, JoinType.RIGHT, JoinParams.Map);
    }

    public static QueryJoinFilter innerListJoin(String table) {
        return new QueryJoinFilter(table, JoinType.INNER, JoinParams.List);
    }

    public static QueryJoinFilter innerSetJoin(String table) {
        return new QueryJoinFilter(table, JoinType.INNER, JoinParams.Set);
    }

    public static QueryJoinFilter innerMapJoin(String table) {
        return new QueryJoinFilter(table, JoinType.INNER, JoinParams.Map);
    }
}
