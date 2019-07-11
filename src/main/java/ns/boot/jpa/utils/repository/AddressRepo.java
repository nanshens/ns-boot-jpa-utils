package ns.boot.jpa.utils.repository;

import ns.boot.jpa.utils.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface AddressRepo extends JpaRepository<Address, String>, JpaSpecificationExecutor<Address>, QuerydslPredicateExecutor<Address> {
}
