package ns.boot.jpa.utils.repository;

import ns.boot.jpa.utils.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, String>, JpaSpecificationExecutor<Customer> {
}
