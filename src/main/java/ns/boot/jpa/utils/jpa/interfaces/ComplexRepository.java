package ns.boot.jpa.utils.jpa.interfaces;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author zn
 */
public interface ComplexRepository<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
	
}
