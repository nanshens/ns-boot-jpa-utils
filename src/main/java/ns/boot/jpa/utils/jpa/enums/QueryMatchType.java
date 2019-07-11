package ns.boot.jpa.utils.jpa.enums;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import java.util.Collection;
import java.util.EnumSet;

/**
 * @author zn
 */

public enum QueryMatchType {
    EQ("equal", CriteriaBuilder.class, Expression.class, Object.class),
    NE("notEqual", CriteriaBuilder.class, Expression.class, Object.class),
    GT("gt", CriteriaBuilder.class, Expression.class, Number.class),
    LT("lt", CriteriaBuilder.class, Expression.class, Number.class),
    GE("ge", CriteriaBuilder.class, Expression.class, Number.class),
    LE("le", CriteriaBuilder.class, Expression.class, Number.class),
    LIKE("like", CriteriaBuilder.class, Expression.class, String.class),
    IN("in", Path.class, Collection.class),
    ISNULL("isNull", CriteriaBuilder.class, Expression.class),
    ISNOTNULL("isNotNull", CriteriaBuilder.class, Expression.class),
    BETWEEN("between", CriteriaBuilder.class, Expression.class, Comparable.class, Comparable.class);

    private Class pathClass;
    private Class targetClass;
    private Class[] paramTypes;
    private String cbName;

    QueryMatchType(String cbName, Class targetClass, Class pathClass, Class ...paramTypes){
        this.targetClass = targetClass;
        this.pathClass = pathClass;
        this.cbName = cbName;
        this.paramTypes = paramTypes;
    }

    public Class[] getParamTypes(){
        return paramTypes;
    }

    public String getCbName() {
        return cbName;
    }

    public Class getPathClass() {
        return pathClass;
    }

    public Class getTargetClass() {
        return targetClass;
    }

    public static EnumSet<QueryMatchType> getAllTypes(){
        return EnumSet.allOf(QueryMatchType.class);
    }
}
