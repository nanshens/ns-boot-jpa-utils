package ns.boot.jpa.utils.jpa.annotations;


import ns.boot.jpa.utils.jpa.enums.QueryMatchType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryType {
	QueryMatchType value() default QueryMatchType.EQ;
}
