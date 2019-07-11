package ns.boot.jpa.utils.jpa.function;

/**
 * @author zn
 */
@FunctionalInterface
public interface ExceptionFunction<T, R> {
	R apply() throws Exception;

}
