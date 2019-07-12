package ns.boot.jpa.utils.repository;

import ns.boot.jpa.utils.entity.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesOrderRepo extends JpaRepository<SalesOrder, String>, JpaSpecificationExecutor<SalesOrder> {
}
