package ns.boot.jpa.utils.service;

import ns.boot.jpa.utils.entity.Customer;
import ns.boot.jpa.utils.jpa.Query;
import ns.boot.jpa.utils.repository.AddressRepo;
import ns.boot.jpa.utils.repository.CustomerRepo;
import ns.boot.jpa.utils.repository.EmployeeRepo;
import ns.boot.jpa.utils.repository.ItemRepo;
import ns.boot.jpa.utils.repository.JobRepo;
import ns.boot.jpa.utils.repository.SalesOrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author nanshen
 */
@Service
public class TestService {
	@Autowired AddressRepo addressRepo;
	@Autowired CustomerRepo customerRepo;
	@Autowired EmployeeRepo employeeRepo;
	@Autowired ItemRepo itemRepo;
	@Autowired JobRepo jobRepo;
	@Autowired SalesOrderRepo salesOrderRepo;

	public void getCustomer() {
		Query<Customer> query = new Query<>();
		
	}
}
