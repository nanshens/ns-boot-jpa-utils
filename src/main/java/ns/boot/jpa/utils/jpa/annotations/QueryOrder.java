package ns.boot.jpa.utils.jpa.annotations;


import ns.boot.jpa.utils.jpa.enums.QueryOrderDirection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryOrder {
	QueryOrderDirection value();
}
