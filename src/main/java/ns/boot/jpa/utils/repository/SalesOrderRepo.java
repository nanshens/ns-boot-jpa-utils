package ns.boot.jpa.utils.repository;

import ns.boot.jpa.utils.entity.SalesOrder;
import ns.boot.jpa.utils.jpa.interfaces.ComplexRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesOrderRepo extends ComplexRepository<SalesOrder, String> {
}
