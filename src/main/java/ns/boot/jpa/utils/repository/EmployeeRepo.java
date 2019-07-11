package ns.boot.jpa.utils.repository;

import ns.boot.jpa.utils.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EmployeeRepo extends JpaRepository<Employee, String>, JpaSpecificationExecutor<Employee> {
}
